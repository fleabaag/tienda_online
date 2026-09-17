package com.product.api.service;

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
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**
     * Obtiene las categorías activas
     * 
     * @see com.product.api.service.CategoryService#getActiveCategories()
     */
    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        try {
            return new ResponseEntity<>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    };

}
