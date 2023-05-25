package com.javadev.bookmanager.integration;

import com.javadev.bookmanager.AbstractPostgresTestContainer;
import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.repository.AuthorRepository;
import com.javadev.bookmanager.request.AuthorPostRequestBody;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.javadev.bookmanager.util.GenerateBookAuthorCategory.generateAuthorRequestBody;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorControllerIT extends AbstractPostgresTestContainer {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository repository;

    private Author authorTest;

    private Book bookTest;

    @Test
    public void findAll_ReturnListOfAuthors_WhenSuccessful() {
        ResponseEntity<List<AuthorDTO>> exchange = testRestTemplate.
                exchange("/api/v1/authors", HttpMethod.GET, null,
                         new ParameterizedTypeReference<List<AuthorDTO>>() {
                         });

        List<AuthorDTO> authors = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
    }

    @Test
    public void findByName_ReturnAuthorByName_WhenSuccessful() {
        String authorName = "Joshua Bloch";

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<AuthorDTO>() {
                          }, authorName);

        AuthorDTO author = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(author).isNotNull();
    }

    @Test
    public void findByName_Return404NotFound_WhenAuthorIsNotFound() {
        ResponseEntity<AuthorDTO> entityMessage = testRestTemplate
                .exchange("/api/v1/authors/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<AuthorDTO>() {
                          }, "");

        assertThat(entityMessage).isNotNull();
        assertThat(entityMessage.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAllBooksByAuthor_ReturnListOfBooksByAuthor_WhenSuccessful() {
        String authorTestName = "Joshua Bloch";

        ResponseEntity<List<BookDTO>> exchange = testRestTemplate.
                exchange("/api/v1/authors/{author}/books", HttpMethod.GET, null,
                                                                           new ParameterizedTypeReference<List<BookDTO>>() {

                                                                           }, authorTestName);
        List<BookDTO> books = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(books).isNotNull().isEmpty();
    }

    @Test
    public void addAuthor_ShouldSaveAndReturnSavedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = GenerateBookAuthorCategory.generateAuthorRequestBody();

        ResponseEntity<AuthorDTO> authorResponseEntity = testRestTemplate
                .postForEntity("/api/v1/authors", authorRequest, AuthorDTO.class);

        AuthorDTO authorSaved = authorResponseEntity.getBody();

        assertThat(authorResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(authorSaved).isNotNull();
    }

    @Test
    public void addAuthor_Should400BadRequest_WhenPassEmptyParam(){
        AuthorPostRequestBody authorRequest = GenerateBookAuthorCategory.generateAuthorRequestBody();
        authorRequest.setName("");

        ResponseEntity<AuthorDTO> authorResponseEntity = testRestTemplate
                .postForEntity("/api/v1/authors", authorRequest, AuthorDTO.class);

        assertThat(authorResponseEntity).isNotNull();
        assertThat(authorResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateAuthor_ShouldUpdateAndReturnAuthorUpdatedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();
        long id = 1;

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{id}", HttpMethod.PUT,
                          new HttpEntity<>(authorRequest), AuthorDTO.class, 1);

        AuthorDTO authorDTO = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO.getName()).isEqualTo(authorRequest.getName());
    }

    @Test
    public void deleteAuthor_ShouldDeleteAuthorAndReturnVoid_WhenSuccessful() {
        long id = 1;

        ResponseEntity<Void> exchange = testRestTemplate.
                exchange("/api/v1/authors/{id}", HttpMethod.DELETE, null, Void.class, id);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
