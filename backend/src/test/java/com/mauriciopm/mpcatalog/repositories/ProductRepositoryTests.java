package com.mauriciopm.mpcatalog.repositories;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.mauriciopm.mpcatalog.entities.Product;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import com.mauriciopm.mpcatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;

    private long exintingId;
    private long notExintingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp(){
        exintingId=1;
        notExintingId=1000;
        countTotalProducts =25;


    }

    @Test
    public void returnOptionalShouldProductObjectWhenIdNotNull(){

        Optional<Product> result =repository.findById(exintingId);

        Assertions.assertTrue(result.isPresent());


    }

    @Test
    public void returnOptionalShouldProductObjectWhenIdNull(){

        Optional<Product> product =repository.findById(notExintingId);

        Assertions.assertTrue(product.isEmpty());


    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdSsNull(){

        Product product= Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts+1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){


        repository.deleteById(exintingId);

        Optional<Product>result = repository.findById(exintingId);

        Assertions.assertFalse(result.isPresent());
    }

//    @Test
//    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNotExists(){
//
//        Assertions.assertThrows(EmptyResultDataAccessException.class,()->{
//
//            repository.deleteById(1000L);
//
//        });
//
//
//     / Teste validado somente no SpringBoot 2  \
//
//    }


}
