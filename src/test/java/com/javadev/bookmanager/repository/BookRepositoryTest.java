package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.util.GenerateBookAndAuthor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    private Book book;

    @BeforeEach
    void setup() {
        book = GenerateBookAndAuthor.generateBookTest();
        repository.save(book);
    }

    @AfterEach
    void tearDown() {
        book = null;
        this.repository.deleteAll();
    }

    @Test
    void findByNameIgnoreCase_returnBookByName_WhenSuccessful(){
        Book bookTest = repository.findByNameIgnoreCase(book.getName()).orElse(new Book());
        Assertions
                .assertThat(bookTest)
                .isNotNull()
                .isEqualTo(book);
    }

    @Test
    void findByNameIgnoreCase_ThrowBookNotFoundException_WhenBookIsNotFound(){
        String bookNameTest = "Book Test Exception";
        Assertions.assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> {
                    repository
                            .findByNameIgnoreCase(bookNameTest)
                            .orElseThrow(() -> new BookNotFoundException(""));
                });
    }

}