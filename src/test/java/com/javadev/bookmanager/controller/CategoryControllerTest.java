package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.service.category.CategoryService;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController controller;

    @Mock
    private CategoryService service;

    private Category categoryTest;

    private Book bookTest;

    @BeforeEach
    void setUp() {
        categoryTest = GenerateBookAuthorCategory.generateCategoryTest();
        bookTest = GenerateBookAuthorCategory.generateBookTest();
        categoryTest.setBooks(new HashSet<>(List.of(bookTest)));
    }

    @Test
    public void findAll_ReturnListOfCategories_WhenSuccessful() {
        when(service.listAll())
                .thenReturn(List.of(new CategoryDTO(categoryTest)));
        List<CategoryDTO> categories = controller.findAll().getBody();
        assertThat(categories).isNotNull().isNotEmpty().hasSize(1);
        assertThat(categories.get(0)).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    public void findByName_ReturnCategoryByName_WhenSuccessful() {
        String categoryName = categoryTest.getName();
        when(service.findByName(anyString()))
                .thenReturn(new CategoryDTO(categoryTest));
        CategoryDTO category = controller.findByName(categoryName).getBody();
        assertThat(category).isNotNull().usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    public void findAllBooksByCategory_ReturnListOfBooksByCategory_WhenSuccessful() {
        String categoryName = categoryTest.getName();
        when(service.listAllBooksByCategory(anyString()))
                .thenReturn(List.of(new BookDTO(bookTest)));
        List<BookDTO> booksByCategory = controller.findAllBooksByCategory(categoryName).getBody();
        assertThat(booksByCategory).isNotNull().isNotEmpty().hasSize(1);
        assertThat(booksByCategory.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void addCategory_ShouldSaveCategoryAndReturnCategorySaved_WhenSuccessful() {
        CategoryDTO categoryDTO = GenerateBookAuthorCategory.generateCategoryDTO();
        when(service.save(any(CategoryDTO.class)))
                .thenReturn(categoryDTO);
        CategoryDTO categorySaved = controller.addCategory(categoryDTO).getBody();
        assertThat(categorySaved).isNotNull().usingRecursiveComparison().isEqualTo(categoryDTO);
    }

    @Test
    public void updateCategory_ShouldUpdateCategoryAndReturnCategorySaved_WhenSuccessful() {
        CategoryDTO categoryDTO = GenerateBookAuthorCategory.generateCategoryDTO();
        long id = 1;
        when(service.update(id, categoryDTO))
                .thenReturn(categoryDTO);
        CategoryDTO categoryUpdated = controller.updateCategory(id, categoryDTO).getBody();
        assertThat(categoryUpdated).isNotNull().usingRecursiveComparison().isEqualTo(categoryDTO);
    }

    @Test
    public void deleteCategory_ShouldDeleteCategoryAndReturnVoid_WhenSuccessful() {
        long id = 1;
        doNothing().when(service).delete(id);
        assertThatCode(() -> controller.deleteCategory(id)).doesNotThrowAnyException();
    }

}