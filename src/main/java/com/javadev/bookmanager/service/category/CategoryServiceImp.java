package com.javadev.bookmanager.service.category;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.exceptions.CategoryNotFoundException;
import com.javadev.bookmanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService{

    private final CategoryRepository repository;

    @Override
    public List<CategoryDTO> listAll() {
        return repository.findAll().stream().map(CategoryDTO::new).toList();
    }

    @Override
    public CategoryDTO findById(long id) {
        return repository.findById(id).map(CategoryDTO::new)
                .orElseThrow(() -> new CategoryNotFoundException("Category  with id {" + id + "} wasn't found."));
    }

    @Override
    public CategoryDTO findByName(String name) {
        return repository.findByNameIgnoreCase(name).map(CategoryDTO::new)
                .orElseThrow(() -> new CategoryNotFoundException("Category {" + name + "} wasn't found."));
    }

    @Override
    public List<BookDTO> listAllBooksByCategory(String name) {
       Category category = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category {" + name + "} wasn't found."));
        return category.getBooks()
                .stream().map(BookDTO::new).toList();
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category categorySaved = repository.save(categoryDTO.transformToObject());
        return new CategoryDTO(categorySaved);
    }

    @Override
    @Transactional
    public CategoryDTO update(long id, CategoryDTO categoryDTO) {
        Category categoryToBeUpdated = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category  with id {" + id + "} wasn't found."));
        categoryToBeUpdated.setName(categoryDTO.getName());
        repository.save(categoryToBeUpdated);
        return new CategoryDTO(categoryToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category  with id {" + id + "} wasn't found."));
        repository.delete(category);
    }
}
