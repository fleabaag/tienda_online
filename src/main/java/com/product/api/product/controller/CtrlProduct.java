package com.product.api.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.product.service.CategoryService;
import com.product.api.product.entity.Category;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CtrlProduct {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World desde la P2";
    }

    @GetMapping()
    public List<Category> getAllCategories() {
        
        CategoryService catService = new CategoryService();

        catService.createCategory(new Category("Calzado", "CLZ", null));
        catService.createCategory(new Category("Tennis", "TNS", 1));
        catService.createCategory(new Category("Casual", "CSL", 1));
        catService.createCategory(new Category("Tecnología", "TCN", null));
        catService.createCategory(new Category("Fotografía", "PHT", 4));
        catService.createCategory(new Category("Cómputo", "CMPT", 4));

        return catService.getCategories();
    }
    

}
