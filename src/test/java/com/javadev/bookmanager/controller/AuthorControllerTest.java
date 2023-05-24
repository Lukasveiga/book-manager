package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.request.AuthorPostRequestBody;
import com.javadev.bookmanager.service.author.AuthorService;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static com.javadev.bookmanager.util.GenerateBookAuthorCategory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @InjectMocks
    private AuthorController controller;

    @Mock
    private AuthorService service;

    private Author authorTest;

    private Book bookTest;

    @BeforeEach
    void setUp() {
        authorTest = generateAuthorTest();
        bookTest = generateBookTest();
        authorTest.setBooks(new HashSet<>(List.of(bookTest)));

    }

    @Test
    public void findAll_ReturnListOfAuthors_WhenSuccessful() {
        when(service.listAll())
                .thenReturn(List.of(new AuthorDTO(authorTest)));

        List<AuthorDTO> authors = controller.findAll().getBody();
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authors.get(0)).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    public void findByName_ReturnAuthorByName_WhenSuccessful(){
        String authorName = authorTest.getName();
        when(service.findByName(anyString()))
                .thenReturn(new AuthorDTO(authorTest));
        AuthorDTO author = controller.findByName(authorName).getBody();
        assertThat(author).isNotNull();
        assertThat(author).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    public void findAllBooksByAuthor_ReturnListOfBooksByAuthor_WhenSuccessful(){
        String authorName = authorTest.getName();
        when(service.listAllBooksByAuthor(anyString()))
                .thenReturn(List.of(new BookDTO(bookTest)));
        List<BookDTO> books = controller.findAllBooksByAuthor(authorName).getBody();
        assertThat(books).isNotNull().isNotEmpty().hasSize(1);
        assertThat(books.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    public void addAuthor_ShouldSaveAndReturnSavedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = GenerateBookAuthorCategory.generateAuthorRequestBody();
        when(service.save(any(AuthorPostRequestBody.class)))
                .thenReturn(new AuthorDTO(authorTest));
        AuthorDTO authorSaved = controller.addAuthor(authorRequest).getBody();
        assertThat(authorSaved).isNotNull().usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    public void updateAuthor_ShouldUpdateAndReturnAuthorUpdatedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();
        long id = 1;
        when(service.update(id, authorRequest))
                .thenReturn(new AuthorDTO(authorTest));
        AuthorDTO authorUpdated = controller.updateAuthor(id, authorRequest).getBody();
        assertThat(authorUpdated).isNotNull().usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    public void deleteAuthor_ShouldDeleteAuthorAndReturnVoid_WhenSuccessful() {
        long id = 1;
        doNothing().when(service).delete(id);
        assertThatCode(() -> controller.deleteAuthor(id)).doesNotThrowAnyException();
    }


}