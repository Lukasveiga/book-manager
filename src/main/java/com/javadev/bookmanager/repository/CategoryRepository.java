package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);
}
