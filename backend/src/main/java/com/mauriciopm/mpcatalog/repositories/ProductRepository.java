package com.mauriciopm.mpcatalog.repositories;

import com.mauriciopm.mpcatalog.dto.ProductDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import com.mauriciopm.mpcatalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    Page<Product> findAll(Pageable pageable);


}
