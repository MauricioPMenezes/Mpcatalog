package com.mauriciopm.mpcatalog.controllers;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.services.CategoryService;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        try {
            CategoryDTO dto = service.findById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }


}
