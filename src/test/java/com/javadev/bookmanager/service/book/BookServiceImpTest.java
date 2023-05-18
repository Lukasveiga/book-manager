package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.repository.BookRepository;
import com.javadev.bookmanager.service.author.AuthorServiceImp;
import com.javadev.bookmanager.util.GenerateBookAndAuthor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImpTest {

    @InjectMocks
    private BookServiceImp service;

    @Mock
    private AuthorServiceImp authorService;

    @Mock
    private BookRepository repository;

    private Book bookTest;

    private Author authorTest;

    @BeforeEach
    void setUp() {
        authorTest = GenerateBookAndAuthor.generateAuthorTest();
        bookTest = GenerateBookAndAuthor.generateBookTest();
        bookTest.addAuthor(authorTest);
    }

    @Test
    void listAll_ReturnListOfBooks_WhenSuccessful() {
        when(repository.findAll())
                .thenReturn(List.of(bookTest));

        List<BookDTO> bookDTOS = service.listAll();
        assertThat(bookDTOS).isNotNull().isNotEmpty().hasSize(1);
        assertThat(bookDTOS.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void findByName_ReturnBookByName_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(bookTest));

        Book book = service.findByName(bookTest.getName());
        assertThat(book).isNotNull();
        assertThat(book).usingRecursiveComparison().isEqualTo(bookTest);
    }

    @Test
    void findByName_ThrowsBookNotFoundException_WhenBookIsNotFound() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenThrow(BookNotFoundException.class);

        assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> service.findByName(""));
    }

    @Test
    void save_ReturnBookDTO_WhenSuccessful() {
        when(repository.save(any(Book.class)))
                .thenReturn(bookTest);

        BookDTO saveBookDTO = service.save(new BookDTO(bookTest));
        assertThat(saveBookDTO).isNotNull();
        assertThat(saveBookDTO).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void insertAuthor_ReturnBookDTO_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(bookTest));

        when(authorService.findByName(any(String.class)))
                .thenReturn(authorTest);

        BookDTO bookDTO = service.insertAuthor(bookTest.getName(), authorTest.getName());
        assertThat(bookDTO).isNotNull();
        assertThat(new ArrayList<>(bookDTO.getAuthors()).get(0))
                .isEqualTo(authorTest.getName());
    }
}