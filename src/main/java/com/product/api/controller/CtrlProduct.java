package com.product.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.service.CategoryServiceImp;
import com.product.api.entity.Category;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

    final CategoryServiceImp svc;

    CtrlCategory(CategoryServiceImp svc){
        this.svc = svc;
    }


    @GetMapping()
    public List<Category> getCategories)
    {
        return svc.getCategories();
    }

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World desde la P2";
    }

    @GetMapping("get")
    public List<Category> getAllCategories() {

        CategoryServiceImp catService = new CategoryServiceImp(null);

        catService.createCategory(new Category("Calzado", "CLZ", null));
        catService.createCategory(new Category("Tennis", "TNS", 1));
        catService.createCategory(new Category("Casual", "CSL", 1));
        catService.createCategory(new Category("Tecnología", "TCN", null));
        catService.createCategory(new Category("Fotografía", "PHT", 4));
        catService.createCategory(new Category("Cómputo", "CMPT", 4));

        return catService.getCategories();
    }

}
