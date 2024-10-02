package com.mauriciopm.mpcatalog.services;

import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.repositories.CategoryRepository;
import com.mauriciopm.mpcatalog.repositories.ProductRepository;
import com.mauriciopm.mpcatalog.services.exceptions.DatabaseException;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import com.mauriciopm.mpcatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private Category category;
    private ProductDTO dto;



    @BeforeEach
    void setUp() throws Exception{
        existingId =1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(product));
        dto = Factory.createProductDTO();



        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));

        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);


        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExisting(){

        ProductDTO result = service.update(existingId, dto);
        Assertions.assertNotNull(result);

    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenIdNotExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class,()-> {
            service.update(nonExistingId,dto);
        });

    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){

        Assertions.assertDoesNotThrow(()-> {
            service.delete(existingId);
        });

        Mockito.verify(repository).deleteById(existingId);
    }


    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows(ResourceNotFoundException.class,()-> {
            service.delete(nonExistingId);
        });

    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId(){

        Assertions.assertThrows(DatabaseException.class,()-> {
            service.delete(dependentId);
        });

    }


    @Test
    public void findAllShouldReturnPaged(){

        Pageable pageable = PageRequest.of(0,10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository,Mockito.times(1)).findAll(pageable);

    }

    @Test
    public void findByIdShouldAndReturnProductDTOWhenIdExisting(){

        ProductDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Mockito.verify(repository,Mockito.times(1)).findById(existingId);

    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdNotExisting() {

        Assertions.assertThrows(ResourceNotFoundException.class,()-> {
            ProductDTO result = service.findById(nonExistingId);
            Mockito.verify(repository,Mockito.times(1)).findById(nonExistingId);

        });

    }

}
