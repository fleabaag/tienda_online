package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
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
    public List<Category> getCategories() {
        return repo.getCategories();
    }

    /**
     * Obtiene las categorías activas
     * 
     * @see com.product.api.service.CategoryService#getActiveCategories()
     */
    @Override
    public List<Category> getActiveCategories() {
        return repo.findByStatusOrderByCategory(1);
    };

}
