package com.mauriciopm.mpcatalog.controllers;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.dto.ProductMinDTO;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.services.ProductService;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(
            @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
            Page<ProductMinDTO> dto = service.findAll(name,pageable);
            return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        try {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }

}