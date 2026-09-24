package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
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
    public ResponseEntity<List<Category>> findAll() {
        try {
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
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
    public ResponseEntity<List<Category>> findActive() {
        return new ResponseEntity<>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
    };

    /**
     * Obtiene las categorías hijas a partir del id
     * 
     * @see com.product.api.service.CategoryService#findChilds(java.lang.Integer)
     */
    @Override
    public ResponseEntity<List<Category>> findChilds(Integer id) {
        // TODO:
        return null;
    }

    /**
     * Crea una categoría nueva
     * 
     * @see com.product.api.service.CategoryService#create(com.product.api.dto.DtoCategoryIn)
     */
    @Override
    public void create(DtoCategoryIn dto) {
        // TODO:
    }

    /**
     * Actualiza una categoría via id
     * 
     * @see com.product.api.service.CategoryService#update(com.product.api.dto.DtoCategoryIn,
     *      java.lang.Integer)
     */
    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        // TODO Auto-generated method stub
    }

    /**
     * Activa de nuevo una categoría desactivada
     * 
     * @see com.product.api.service.CategoryService#enable(java.lang.Integer)
     */
    @Override
    public void enable(Integer id) {
        // TODO Auto-generated method stub

    }

    /**
     * Desactiva una categoría sólo si se encuentra activa
     * 
     * @see com.product.api.service.CategoryService#disable(java.lang.Integer)
     */
    @Override
    public void disable(Integer id) {
        // TODO Auto-generated method stub

    }

}
