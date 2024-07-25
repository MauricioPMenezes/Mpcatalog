package com.mauriciopm.mpcatalog.repositories;

import com.mauriciopm.mpcatalog.dto.CategoryDTO;
import com.mauriciopm.mpcatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{


}
