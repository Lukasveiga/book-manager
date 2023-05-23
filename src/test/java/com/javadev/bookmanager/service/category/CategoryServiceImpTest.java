package com.javadev.bookmanager.service.category;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.exceptions.CategoryNotFoundException;
import com.javadev.bookmanager.repository.CategoryRepository;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImpTest {

    @InjectMocks
    private CategoryServiceImp service;

    @Mock
    private CategoryRepository repository;

    private Category categoryTest;

    private Book bookTest;


    @BeforeEach
    void setUp() {
        categoryTest = GenerateBookAuthorCategory.generateCategoryTest();
        bookTest = GenerateBookAuthorCategory.generateBookTest();
        categoryTest.setBooks(new HashSet<>(List.of(bookTest)));
    }

    @Test
    void listAll_ReturnListOfCategories_WhenSuccessful() {
        when(repository.findAll())
                .thenReturn(List.of(categoryTest));

        List<CategoryDTO> category = service.listAll();
        assertThat(category).isNotNull().isNotEmpty().hasSize(1);
        assertThat(category.get(0)).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    void findById_ReturnCategoryById_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(categoryTest));

        CategoryDTO categoryDTO = service.findById(1);
        assertThat(categoryDTO).isNotNull();
        assertThat(categoryDTO).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    void findById_ThrowsCategoryNotFoundException_WhenAuthorIsNotFound() {
        when(repository.findById(anyLong()))
                .thenThrow(CategoryNotFoundException.class);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> service.findById(1));
    }

    @Test
    void findByName_ReturnCategoryByName_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(categoryTest));

        CategoryDTO categoryDTO = service.findByName(categoryTest.getName());
        assertThat(categoryDTO).isNotNull();
        assertThat(categoryDTO).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    void findByName_ThrowsCategoryNotFoundException_WhenAuthorIsNotFound() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenThrow(CategoryNotFoundException.class);

        assertThatExceptionOfType(CategoryNotFoundException.class)
                .isThrownBy(() -> service.findByName(""));
    }

    @Test
    void listAllBooksByCategory_ReturnBookForCategory_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(categoryTest));

        List<BookDTO> bookDTOS = service.listAllBooksByCategory(categoryTest.getName());
        assertThat(bookDTOS).isNotNull().isNotEmpty().hasSize(1);
        assertThat(bookDTOS.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void save_ReturnBookForCategory_WhenSuccessful() {
        when(repository.save(any(Category.class)))
                .thenReturn(categoryTest);

        CategoryDTO saveCategoryDTO = service.save(GenerateBookAuthorCategory.generateCategoryDTO());
        assertThat(saveCategoryDTO).isNotNull();
        assertThat(saveCategoryDTO).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    void update_ReturnCategoryUpdated_WhenSuccessful(){
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(categoryTest));

        long id = 1;
        CategoryDTO categoryUpdated = service.update(1, GenerateBookAuthorCategory.generateCategoryDTO());
        assertThat(categoryUpdated).isNotNull();
        assertThat(categoryUpdated).usingRecursiveComparison().isEqualTo(new CategoryDTO(categoryTest));
    }

    @Test
    void delete_ReturnVoid_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(categoryTest));

        doNothing().when(repository).delete(any(Category.class));

        assertThatCode(() -> service.delete(1))
                .doesNotThrowAnyException();
    }

}