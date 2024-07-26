package com.mauriciopm.mpcatalog.services;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.repositories.CategoryRepository;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = repository.findAll();
        List<CategoryDTO> result=list.stream().map(x->new CategoryDTO(x)).collect(Collectors.toList());
        return result;
    }

    @Transactional(readOnly = true)
    public CategoryDTO  findById(Long id) {
        Category list = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Recurso não encontrado!"));
        return new CategoryDTO(list);
    }
}
