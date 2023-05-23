package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    private Category category;

    @BeforeEach
    void setup() {
        category = GenerateBookAuthorCategory.generateCategoryTest();
        repository.save(category);
    }

    @AfterEach
    void tearDown() {
        category = null;
        this.repository.deleteAll();
    }

    @Test
    void findByNameIgnoreCase_returnBookByName_WhenSuccessful(){
        Category categoryTest = repository.findByNameIgnoreCase(category.getName()).orElse(new Category());
        Assertions
                .assertThat(categoryTest)
                .isNotNull()
                .isEqualTo(categoryTest);
    }

    @Test
    void findByNameIgnoreCase_ThrowCategoryNotFoundException_WhenCategoryIsNotFound(){
        String categoryTestName = "Category Test Exception";
        Assertions.assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> {
                    repository
                            .findByNameIgnoreCase(categoryTestName)
                            .orElseThrow(() -> new BookNotFoundException(""));
                });
    }

}