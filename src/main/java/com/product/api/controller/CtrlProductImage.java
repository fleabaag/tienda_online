package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.service.SvcProductImage;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("product/{id}/image")
public class CtrlProductImage {

    @Autowired
    SvcProductImage svc;

    CtrlProductImage(SvcProductImage svc) {
        this.svc = svc;
    }

    @GetMapping()
    public List<DtoProductImageOut> getProductImages(@PathVariable Integer id) {
        return svc.getProductImages(id);
    }

    @PostMapping()
    public ResponseEntity<String> createProductImage(@PathVariable Integer id, @Valid @RequestBody DtoProductImageIn in) {
        return svc.createProductImage(id, in);
    }

    @DeleteMapping("/{productImageid}")
    public ResponseEntity<String> deleteProductImage(@PathVariable Integer id, @PathVariable Integer productImageid) {
        return svc.deleteProductImage(id, productImageid);
    }

}
