package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.product.api.service.SvcProductImage;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("product/{id}/image")
public class CtrlProductImage {
    
    @Autowired 
    SvcProductImage svc;
    

    @GetMapping()
    public List<ResponseEntity<String>> getProductImages(@RequestParam String param) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<String> createProductImage(@RequestBody String entity) {
        //TODO: process POST request
        
        return new ResponseEntity<String>("La imagen ha sido registrada", HttpStatus.OK);
    }


    @DeleteMapping("/{productImageid}") 
    public ResponseEntity<String> deleteProductImage()
    {
        return new ResponseEntity<String>("La imagen ha sido eliminada", HttpStatus.OK);
    }

    
}
