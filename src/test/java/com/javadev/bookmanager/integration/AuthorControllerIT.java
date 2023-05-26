package com.javadev.bookmanager.integration;

import com.javadev.bookmanager.AbstractPostgresTestContainer;
import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.repository.AuthorRepository;
import com.javadev.bookmanager.request.AuthorPostRequestBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.javadev.bookmanager.util.EntitiesInformationDatabaseTest.AUTHOR_TEST_NAME_IN_DB;
import static com.javadev.bookmanager.util.EntitiesInformationDatabaseTest.BOOK_TEST_NAME_IN_DB;
import static com.javadev.bookmanager.util.GenerateBookAuthorCategory.generateAuthorRequestBody;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorControllerIT extends AbstractPostgresTestContainer {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository repository;

    @Test
    public void findAll_ReturnListOfAuthors_WhenSuccessful() {
        ResponseEntity<List<AuthorDTO>> exchange = testRestTemplate.
                exchange("/api/v1/authors", HttpMethod.GET, null,
                         new ParameterizedTypeReference<>() {
                         });

        List<AuthorDTO> authors = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authors.get(0).getName()).isEqualTo(AUTHOR_TEST_NAME_IN_DB);
    }

    @Test
    public void findByName_ReturnAuthorByName_WhenSuccessful() {
        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, AUTHOR_TEST_NAME_IN_DB);

        AuthorDTO author = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo(AUTHOR_TEST_NAME_IN_DB);
    }

    @Test
    public void findByName_Return404NotFound_WhenAuthorIsNotFoundByName() {
        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, "");

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAllBooksByAuthor_ReturnListOfBooksByAuthor_WhenSuccessful() {
        ResponseEntity<List<BookDTO>> exchange = testRestTemplate.
                exchange("/api/v1/authors/{author}/books", HttpMethod.GET, null,
                         new ParameterizedTypeReference<>() {

                         }, AUTHOR_TEST_NAME_IN_DB);
        List<BookDTO> books = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TEST_NAME_IN_DB);
    }

    @Test
    public void addAuthor_ShouldSaveAndReturnSavedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/authors", authorRequest, AuthorDTO.class);

        AuthorDTO authorSaved = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(authorSaved).isNotNull();
        assertThat(authorSaved.getName()).isEqualTo(authorRequest.getName());
    }

    @Test
    public void addAuthor_Should400BadRequest_WhenPassEmptyParam(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();
        authorRequest.setName("");

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/authors", authorRequest, AuthorDTO.class);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateAuthor_ShouldUpdateAndReturnAuthorUpdatedAuthor_WhenSuccessful(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();
        authorRequest.setName(AUTHOR_TEST_NAME_IN_DB);
        long id = 1;

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{id}", HttpMethod.PUT,
                          new HttpEntity<>(authorRequest), AuthorDTO.class, id);

        AuthorDTO authorDTO = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorDTO).isNotNull();
        assertThat(authorDTO.getName()).isEqualTo(authorRequest.getName());
    }

    @Test
    public void updateAuthor_Return404NotFound_WhenAuthorIsNotFoundById(){
        AuthorPostRequestBody authorRequest = generateAuthorRequestBody();
        long id = 5;

        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/authors/{id}", HttpMethod.PUT,
                          new HttpEntity<>(authorRequest), AuthorDTO.class, id);

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
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
