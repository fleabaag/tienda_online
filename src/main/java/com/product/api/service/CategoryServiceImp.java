package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;
import com.product.exception.DBExceptionTranslator;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    // @Autowired
    final RepoCategory repo;
    private static final int STATUS_ACTIVE = 1;

    /**
     * Constructor vacío
     */
    CategoryServiceImp(RepoCategory repo) {
        this.repo = repo;
    }

    /* ------- FUNCIONALIDADES PARA CATEGORY ------- */

    /**
     * Obtiene todas las categorías registradas
     * 
     * @return arreglo de las categorías
     */
    @Override
    public List<Category> findAll() {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**
     * Obtiene las categorías activas
     * 
     * @see com.product.api.service.CategoryService#findActive()
     */
    @Override
    public List<Category> findActive() {
        try {
            return repo.findByStatusOrderByCategory(1);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    };

    /**
     * Obtiene las categorías hijas a partir del id
     * 
     * @see com.product.api.service.CategoryService#findChilds(java.lang.Integer)
     */
    @Override
    public List<Category> findChilds(Integer id) {
        try {
            return repo.findChilds(id);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**
     * Crea una categoría nueva
     * 
     * @see com.product.api.service.CategoryService#create(com.product.api.dto.DtoCategoryIn)
     */
    @Override
    public void create(DtoCategoryIn dto) {
        validateFields(dto);
        String name = dto.getCategory().trim();
        String tag = dto.getTag().trim();

        validateUniqueness(name, tag, -1);
        validateParent(dto.getParentCategoryId(), null);

        try {
            repo.create(name, tag, dto.getParentCategoryId());
        } catch (DataAccessException e) {
            throw DBExceptionTranslator.translate(e, "Error al crear la categoría.");
        }
    }

    /**
     * Actualiza una categoría via id
     * 
     * @see com.product.api.service.CategoryService#update(com.product.api.dto.DtoCategoryIn,
     *      java.lang.Integer)
     */
    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        if (id == null || id <= 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id ingresado no es válido.");

        validateFields(dto);
        String name = dto.getCategory().trim();
        String tag = dto.getTag().trim();

        try {
            if (repo.findById(id).isEmpty())
                throw new ApiException(HttpStatus.NOT_FOUND, "La categoría no existe.");

            validateUniqueness(name, tag, id);
            validateParent(dto.getParentCategoryId(), id);

            repo.update(id, name, tag, dto.getParentCategoryId());
        } catch (DataAccessException e) {
            throw DBExceptionTranslator.translate(e, "Error al actualizar la categoría.");
        }
    }

    /**
     * Activa de nuevo una categoría desactivada
     * 
     * @see com.product.api.service.CategoryService#enable(java.lang.Integer)
     */
    @Override
    public void enable(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id ingresado no es válido");
        }
        try {
            repo.updateStatus(id, 1);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría.");
        }

    }

    /**
     * Desactiva una categoría sólo si se encuentra activa
     * 
     * @see com.product.api.service.CategoryService#disable(java.lang.Integer)
     */
    @Override
    public void disable(Integer id) {
        if (id == null || id <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "el id ingresado no es válido. Verifica nuevamente");
        }
        try {
            List<Category> children = repo.findChilds(id);
            boolean hasActiveChilds = false;

            if (children != null && !children.isEmpty()) {
                for (Category category : children)
                    if (category.getStatus() == 1) {
                        hasActiveChilds = true;
                        break;
                    }
            }

            if (hasActiveChilds) {
                throw new ApiException(
                        HttpStatus.CONFLICT,
                        "No se puede desactivar la categoría porque tiene al menos una categorías hija activa.");
            }

            repo.updateStatus(id, 0);

        } catch (ApiException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateFields(DtoCategoryIn dto) {
        if (dto == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El cuerpo de la petición es requerido.");
        if (dto.getCategory() == null || dto.getCategory().isBlank())
            throw new ApiException(HttpStatus.BAD_REQUEST, "El nombre de la categoría es requerido.");
        if (dto.getTag() == null || dto.getTag().isBlank())
            throw new ApiException(HttpStatus.BAD_REQUEST, "El tag de la categoría es requerido.");
        // Ajusta los límites a la longitud real de tus columnas
        if (dto.getCategory().trim().length() > 50)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El nombre de la categoría excede la longitud permitida.");
        if (dto.getTag().trim().length() > 50)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El tag de la categoría excede la longitud permitida.");
        if (dto.getParentCategoryId() != null && dto.getParentCategoryId() <= 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El id de la categoría padre no es válido.");
    }

    private void validateUniqueness(String name, String tag, Integer excludeId) {
        try {
            if (repo.countByName(name, excludeId) > 0)
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso.");
            if (repo.countByTag(tag, excludeId) > 0)
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso.");
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**
     * @param parentId id del padre (puede ser null)
     * @param selfId   id de la categoría que se actualiza (null en create)
     */
    @SuppressWarnings("null")
    private void validateParent(Integer parentId, Integer selfId) {
        if (parentId == null)
            return; // categoría raíz

        if (selfId != null && parentId.equals(selfId))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma.");

        try {
            Category parent = repo.findById(parentId)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "La categoría padre no existe."));

            if (parent.getStatus() == null || parent.getStatus() != STATUS_ACTIVE)
                throw new ApiException(HttpStatus.CONFLICT, "La categoría padre está inactiva.");

            // Evita ciclos: el nuevo padre no puede ser descendiente de la categoría actual
            if (selfId != null) {
                Set<Integer> visited = new HashSet<>();
                Integer current = parent.getParentCategory_id();
                while (current != null && visited.add(current)) {
                    if (current.equals(selfId))
                        throw new ApiException(HttpStatus.CONFLICT,
                                "No se puede asignar como padre a una categoría descendiente (ciclo).");
                    current = repo.findById(current).map(Category::getParentCategory_id).orElse(null);
                }
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

}
