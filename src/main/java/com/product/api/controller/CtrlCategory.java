package com.product.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.service.CategoryService;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    final CategoryService csv;

    CtrlCategory(CategoryService csv) {
        this.csv = csv;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(csv.findAll(), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories() {
        return new ResponseEntity<>(csv.findActive(), HttpStatus.OK);
    }

    @GetMapping("/{id}/childs")
    public ResponseEntity<List<Category>> getMethodName(@PathVariable Integer id) {
        return new ResponseEntity<>(csv.findChilds(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody DtoCategoryIn dto) {
        csv.create(dto);
        return ResponseEntity.ok().body("La categoría ha sido registrada.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable DtoCategoryIn dto, @RequestBody Integer id) {
        csv.update(dto, id);
        return ResponseEntity.ok().body("La categoría ha sido actualizada.");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        csv.enable(id);
        return ResponseEntity.ok().body("La categoría ha sido activada.");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        csv.disable(id);
        return ResponseEntity.ok().body("La categoría ha sido desactivada.");
    }

}
