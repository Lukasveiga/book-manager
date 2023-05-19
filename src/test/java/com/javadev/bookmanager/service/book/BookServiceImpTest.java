package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
import com.javadev.bookmanager.repository.BookRepository;
import com.javadev.bookmanager.request.BookPostRequestBody;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImpTest {

    @InjectMocks
    private BookServiceImp service;

    @Mock
    private AuthorRepository authorRepository;

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
    void findById_ReturnBookById_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(bookTest));

        BookDTO bookDTO = service.findById(1);
        assertThat(bookDTO).isNotNull();
        assertThat(bookDTO).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void findById_ThrowsBookNotFoundException_WhenAuthorIsNotFound() {
        when(repository.findById(anyLong()))
                .thenThrow(BookNotFoundException.class);

        assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> service.findById(1));
    }


    @Test
    void findByName_ReturnBookByName_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(bookTest));

        BookDTO book = service.findByName(bookTest.getName());
        assertThat(book).isNotNull();
        assertThat(book).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
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

        BookDTO saveBookDTO = service
                .save(new BookPostRequestBody(bookTest.getName(), bookTest.getYear(), bookTest.getPages()));
        assertThat(saveBookDTO).isNotNull();
        assertThat(saveBookDTO).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void insertAuthor_ReturnBookDTO_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(bookTest));

        when(authorRepository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(authorTest));

        BookDTO bookDTO = service.insertAuthor(bookTest.getName(), authorTest.getName());
        assertThat(bookDTO).isNotNull();
        assertThat(new ArrayList<>(bookDTO.getAuthors()).get(0))
                .isEqualTo(authorTest.getName());
    }

    @Test
    void update_ReturnBookUpdated_WhenSuccessful(){
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(bookTest));

        long id = 1;
        BookDTO bookUpdated = service.update(1, GenerateBookAndAuthor.generateBookRequestBody());
        assertThat(bookUpdated).isNotNull();
        assertThat(bookUpdated).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void delete_ReturnVoid_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(bookTest));

        doNothing().when(repository).delete(any(Book.class));

        assertThatCode(() -> service.delete(1))
                .doesNotThrowAnyException();
    }
}