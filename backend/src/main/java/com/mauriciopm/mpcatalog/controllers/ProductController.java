package com.mauriciopm.mpcatalog.controllers;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.dto.ProductMinDTO;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(Pageable pageable) {
        Page<ProductMinDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

}