package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
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
class AuthorServiceImpTest {

    @InjectMocks
    private AuthorServiceImp service;

    @Mock
    private AuthorRepository repository;

    private Author authorTest;

    private Book bookTest;


    @BeforeEach
    void setUp() {
        authorTest = GenerateBookAuthorCategory.generateAuthorTest();
        bookTest = GenerateBookAuthorCategory.generateBookTest();
        authorTest.setBooks(new HashSet<>(List.of(bookTest)));
    }

    @Test
    void listAll_ReturnListOfAuthors_WhenSuccessful() {
        when(repository.findAll())
                .thenReturn(List.of(authorTest));

        List<AuthorDTO> authors = service.listAll();
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authors.get(0)).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void findById_ReturnAuthorById_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(authorTest));

        AuthorDTO authorDTO = service.findById(1);
        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void findById_ThrowsAuthorNotFoundException_WhenAuthorIsNotFound() {
        when(repository.findById(anyLong()))
                .thenThrow(AuthorNotFoundException.class);

        assertThatExceptionOfType(AuthorNotFoundException.class)
                .isThrownBy(() -> service.findById(1));
    }

    @Test
    void findByName_ReturnAuthorByName_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(authorTest));

        AuthorDTO authorDTO = service.findByName(authorTest.getName());
        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void findByName_ThrowsAuthorNotFoundException_WhenAuthorIsNotFound() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenThrow(AuthorNotFoundException.class);

        assertThatExceptionOfType(AuthorNotFoundException.class)
                .isThrownBy(() -> service.findByName(""));
    }

    @Test
    void listAllBooksByAuthor_ReturnBookForAuthor_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(authorTest));

        List<BookDTO> bookDTOS = service.listAllBooksByAuthor(authorTest.getName());
        assertThat(bookDTOS).isNotNull().isNotEmpty().hasSize(1);
        assertThat(bookDTOS.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void save_ReturnBookForAuthor_WhenSuccessful() {
        when(repository.save(any(Author.class)))
                .thenReturn(authorTest);

        AuthorDTO saveAuthorDTO = service.save(GenerateBookAuthorCategory.generateAuthorRequestBody());
        assertThat(saveAuthorDTO).isNotNull();
        assertThat(saveAuthorDTO).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void update_ReturnAuthorUpdated_WhenSuccessful(){
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(authorTest));

        long id = 1;
        AuthorDTO authorUpdated = service.update(1, GenerateBookAuthorCategory.generateAuthorRequestBody());
        assertThat(authorUpdated).isNotNull();
        assertThat(authorUpdated).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void delete_ReturnVoid_WhenSuccessful() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(authorTest));

        doNothing().when(repository).delete(any(Author.class));

        assertThatCode(() -> service.delete(1))
                .doesNotThrowAnyException();
    }

}