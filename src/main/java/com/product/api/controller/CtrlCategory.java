package com.product.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.service.CategoryService;
import com.product.api.entity.Category;
import java.util.List;
// import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    final CategoryService csv;

    CtrlCategory(CategoryService csv) {
        this.csv = csv;
    }

    @GetMapping()
    public List<Category> getCategories() {
        return csv.getCategories();
    }

    @GetMapping("/active")
    public List<Category> getActiveCategories() {
        return csv.getActiveCategories();
    }


    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World desde la P3";
    }


}
