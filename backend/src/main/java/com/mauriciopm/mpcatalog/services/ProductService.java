package com.mauriciopm.mpcatalog.services;

import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.dto.ProductMinDTO;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.repositories.ProductRepository;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name,Pageable pageable){
        Page<Product> result = repository.searchByName(name,pageable);
        return result.map(x->new ProductMinDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product list = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso não encontrado!"));
        return new ProductDTO(list);
    }
}

