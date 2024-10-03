package com.mauriciopm.mpcatalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.services.ProductService;
import com.mauriciopm.mpcatalog.services.exceptions.DatabaseException;
import com.mauriciopm.mpcatalog.services.exceptions.ResourceNotFoundException;
import com.mauriciopm.mpcatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService service;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private long existingId;
    private long nonExistingId;
    private long dependentID;

    @BeforeEach
    void setUp() throws Exception{
        existingId =1L;
        nonExistingId = 2L;
        dependentID = 3L;
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        when(service.findAllPaged(any())).thenReturn(page);

        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(service.update(eq(existingId),any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingId),any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentID);

        when(service.insert(any())).thenReturn(productDTO);

    }
    @Test
    public void insertShouldProductDTO() throws  Exception{

        ResultActions result ;
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        result =  mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());

    }

    @Test
    public void deleteShouldProductWhenIdExists() throws  Exception{

        ResultActions result =  mockMvc.perform(delete("/products/{id}",existingId)
                .accept((MediaType.APPLICATION_JSON)));
        result.andExpect(status().isNoContent());

    }

    @Test
    public void deleteShouldResourceNotFoundExceptionWhenIdDoesNotExists() throws  Exception{

        ResultActions result =  mockMvc.perform(delete("/products/{id}",nonExistingId)
                .accept((MediaType.APPLICATION_JSON)));
        result.andExpect(status().isNotFound());

    }

    @Test
    public void findAllShouldReturnPage() throws  Exception{

        ResultActions result =  mockMvc.perform(get("/products")
                .accept((MediaType.APPLICATION_JSON)));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws  Exception{

        ResultActions result =  mockMvc.perform(get("/products/{id}",existingId)
                .accept((MediaType.APPLICATION_JSON)));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());

    }


    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExisting() throws  Exception{

        ResultActions result =  mockMvc.perform(get("/products/{id}",nonExistingId)
                .accept((MediaType.APPLICATION_JSON)));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

        ResultActions result ;
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        result =  mockMvc.perform(put("/products/{id}",existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());
    }



    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        ResultActions result ;
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        result =  mockMvc.perform(put("/products/{id}",nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

      }
}




