package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    private Book book;

    @BeforeEach
    void setup() {
        book = GenerateBookAuthorCategory.generateBookTest();
        repository.save(book);
    }

    @AfterEach
    void tearDown() {
        book = null;
        this.repository.deleteAll();
    }

    @Test
    void findByTitleIgnoreCase_ReturnBookByName_WhenSuccessful(){
        Book bookTest = repository.findByTitleIgnoreCase(book.getTitle()).orElse(new Book());
        Assertions
                .assertThat(bookTest)
                .isNotNull()
                .isEqualTo(book);
    }

    @Test
    void findByTitleIgnoreCase_ThrowBookNotFoundException_WhenBookIsNotFound(){
        String bookNameTest = "Book Test Exception";
        Assertions.assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> {
                    repository
                            .findByTitleIgnoreCase(bookNameTest)
                            .orElseThrow(() -> new BookNotFoundException(""));
                });
    }

    @Test
    void findByIsAvailableTrue_ReturnListOfAvailableBooks_WhenSuccessful(){
        List<Book> byIsAvailableTrue = repository.findByIsAvailableTrue();

        Assertions.assertThat(byIsAvailableTrue).isNotEmpty();
        Assertions.assertThat(byIsAvailableTrue.get(0).isAvailable()).isTrue();

    }

}