package com.product.api.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Category>> findAll() {
        return csv.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return csv.findActive();
    }

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World desde la P4";
    }



}
