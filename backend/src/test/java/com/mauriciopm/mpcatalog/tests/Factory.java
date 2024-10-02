package com.mauriciopm.mpcatalog.tests;

import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Mac", "Mac Pro 10 Top", 2500.0, "https://img.com/img.png");
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product,product.getCategories());
    }

    public static Category createCategory(){
        return new Category(1L,"Eletronics");


    }
}
