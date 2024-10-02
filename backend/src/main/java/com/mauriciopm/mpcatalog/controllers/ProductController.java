package com.mauriciopm.mpcatalog.controllers;

import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.dto.ProductMinDTO;
import com.mauriciopm.mpcatalog.services.ProductService;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.ClientInfoStatus;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService service;


//    @GetMapping
//    public ResponseEntity<Page<ProductMinDTO>> findAll(
//            @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
//        try {
//            Page<ProductMinDTO> dto = service.findAll(name,pageable);
//            return ResponseEntity.ok(dto);
//        } catch (EntityNotFoundException e) {
//            throw new ResourceNotFoundException("Recurso não encontrado!");
//        }
//    }


    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAllPaged(Pageable pageable){
        Page<ProductDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

//    @GetMapping
//    public ResponseEntity<Page<ProductMinDTO>> findAll(
//            @RequestParam(value = "name" , defaultValue ="" ) String  name,
//            Pageable pageable){
//        Page<ProductMinDTO> list = service.findAll( name,pageable);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        try {
            ProductDTO dto = service.findById(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }



    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,@Valid @RequestBody ProductDTO dto) {
        try {
            dto = service.update(id, dto);
            return ResponseEntity.ok().body(dto);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }


    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete( @PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }
}

