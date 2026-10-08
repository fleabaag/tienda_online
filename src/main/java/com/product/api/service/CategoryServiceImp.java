package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    // @Autowired
    final RepoCategory repo;

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
        try {
            // TODO: se hacen las validaciones aqúi
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            String msg = e.getLocalizedMessage();
            if (msg != null && msg.contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso.");
            if (msg != null && msg.contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "EL tag de la categoría ya está en uso.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría.");
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
        try {
            repo.update(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch (DataAccessException e) {
            String msg = e.getLocalizedMessage();
            if (msg != null && msg.contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso.");
            if (msg != null && msg.contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso.");
            if (msg != null && msg.contains("ux_parent_category_id"))
                throw new ApiException(HttpStatus.CONFLICT,
                        "El id parent es inválido o la categoría parent está inactiva.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al actualizar la categoría.");
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

}
