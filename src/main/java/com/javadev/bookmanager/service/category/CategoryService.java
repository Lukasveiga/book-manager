package com.javadev.bookmanager.service.category;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> listAll();

    List<BookDTO> listAllBooksByCategory(String categoryName);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO findByName(String name);

    CategoryDTO findById(long id);

    CategoryDTO update(long id, CategoryDTO categoryDTO);

    void delete(long id);
}
