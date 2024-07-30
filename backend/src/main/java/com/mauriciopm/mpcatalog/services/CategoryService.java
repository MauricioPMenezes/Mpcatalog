package com.mauriciopm.mpcatalog.services;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.repositories.CategoryRepository;
import com.mauriciopm.mpcatalog.services.exceptions.DatabaseException;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        List<CategoryDTO> result = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        return result;
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category list = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado!"));
        return new CategoryDTO(list);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        if (repository.existsByName(dto.getName())) {
            // Caso uma categoria com o mesmo nome já exista, pode lançar uma exceção ou retornar uma mensagem
            throw new DatabaseException("Uma Categoria com este nome ja Existe");
        }
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id,CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);

        } catch (EntityNotFoundException e) {

            throw new ResourceNotFoundException("ID " + id + " NOT FOUND");

        }

    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ID " + id + " NOT FOUND");
        }
        try{
            repository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Falha de Integridade Referencial");
        }

    }
}
