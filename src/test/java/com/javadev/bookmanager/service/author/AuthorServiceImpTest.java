package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
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
        authorTest = new Author("Author Test Name", "Author Test Description");
        bookTest = new Book("Book Test Title", 9999, 999);
        authorTest.setBooks(new HashSet<Book>(List.of(bookTest)));
    }

    @Test
    void listAll_ReturnListOfAuthors_WhenSuccessful() {
        when(repository.findAll())
                .thenReturn(List.of(authorTest));

        String expectedAuthorName = authorTest.getName();
        List<AuthorDTO> authors = service.listAll();
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authors.get(0)).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }

    @Test
    void findByName_ReturnAuthorByName_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(authorTest));

        Author author = service.findByName(authorTest.getName());
        assertThat(author).isNotNull();
        assertThat(author).usingRecursiveComparison().isEqualTo(authorTest);
    }

    @Test
    void findByName_ThrowsAuthorNotFound_WhenAuthorIsNotFound() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenThrow(AuthorNotFoundException.class);

        assertThatExceptionOfType(AuthorNotFoundException.class)
                .isThrownBy(() -> service.findByName(""));
    }

    @Test
    void listAllBooksByAuthor_ReturnBookForAuthor_WhenSuccessful() {
        when(repository.findByNameIgnoreCase(any(String.class)))
                .thenReturn(Optional.ofNullable(authorTest));

        List<BookDTO> bookDTOS = service.listAllBooksByAuthor(authorTest.getName());
        assertThat(bookDTOS).isNotNull().isNotEmpty().hasSize(1);
        assertThat(bookDTOS.get(0)).usingRecursiveComparison().isEqualTo(new BookDTO(bookTest));
    }

    @Test
    void save_ReturnBookForAuthor_WhenSuccessful() {
        when(repository.save(any(Author.class)))
                .thenReturn(authorTest);

        AuthorDTO saveAuthorDTO = service.save(new AuthorDTO(authorTest));
        assertThat(saveAuthorDTO).isNotNull();
        assertThat(saveAuthorDTO).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorTest));
    }
}