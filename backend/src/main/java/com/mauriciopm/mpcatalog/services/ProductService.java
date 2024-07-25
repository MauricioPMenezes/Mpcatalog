package com.mauriciopm.mpcatalog.services;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.repositories.CategoryRepository;
import com.mauriciopm.mpcatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public List<Product> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product list = repository.findById(id).get();
        return new ProductDTO(list);
    }
}

