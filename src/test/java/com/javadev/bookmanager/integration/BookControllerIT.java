package com.javadev.bookmanager.integration;

import com.javadev.bookmanager.AbstractPostgresTestContainer;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.repository.BookRepository;
import com.javadev.bookmanager.request.BookPostRequestBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.javadev.bookmanager.util.EntitiesInformationDatabaseTest.*;
import static com.javadev.bookmanager.util.GenerateBookAuthorCategory.generateBookRequestBody;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BookRepository repository;

    @Test
    public void findAll_ReturnListOfBooks_WhenSuccessful() {
        ResponseEntity<List<BookDTO>> exchange = testRestTemplate.
                exchange("/api/v1/books", HttpMethod.GET, null,
                         new ParameterizedTypeReference<>() {
                         });

        List<BookDTO> books = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(books).isNotNull().isNotEmpty().hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TEST_NAME_IN_DB);
    }

    @Test
    public void findByTitle_ReturnBookByName_WhenSuccessful() {
        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/{title}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, BOOK_TEST_NAME_IN_DB);

        BookDTO book = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(BOOK_TEST_NAME_IN_DB);
    }

    @Test
    public void findByName_Return404NotFound_WhenBookIsNotFoundByName() {
        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/{title}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, "");

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void addBook_ShouldSaveAndReturnSavedBook_WhenSuccessful(){
        BookPostRequestBody bookRequest = generateBookRequestBody();

        ResponseEntity<BookDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/books", bookRequest, BookDTO.class);

        BookDTO bookSaved = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(bookSaved).isNotNull();
        assertThat(bookSaved.getTitle()).isEqualTo(bookRequest.getTitle());
    }

    @Test
    public void addBook_Should400BadRequest_WhenPassEmptyParam(){
        BookPostRequestBody bookRequest = generateBookRequestBody();
        bookRequest.setTitle("");

        ResponseEntity<BookDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/books", bookRequest, BookDTO.class);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateBook_ShouldUpdateAndReturnUpdatedBook_WhenSuccessful(){
        BookPostRequestBody bookRequest = generateBookRequestBody();
        bookRequest.setTitle(BOOK_TEST_NAME_IN_DB);
        long id = 1;

        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/{id}", HttpMethod.PUT,
                          new HttpEntity<>(bookRequest), BookDTO.class, id);

        BookDTO bookDTO = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(bookDTO).isNotNull();
        assertThat(bookDTO.getTitle()).isEqualTo(bookRequest.getTitle());
    }

    @Test
    public void updateBook_Return404NotFound_WhenBookIsNotFoundById(){
        BookPostRequestBody bookRequest = generateBookRequestBody();
        long id = 5;

        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/{id}", HttpMethod.PUT,
                          new HttpEntity<>(bookRequest), BookDTO.class, id);

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteBook_ShouldDeleteBookAndReturnVoid_WhenSuccessful() {
        testRestTemplate
                .postForEntity("/api/v1/books", generateBookRequestBody(), BookDTO.class);

        long id = 2;

        ResponseEntity<Void> exchange = testRestTemplate.
                exchange("/api/v1/books/{id}", HttpMethod.DELETE, null, Void.class, id);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void addAuthorToABook_ShouldReturnBookUpdated_WhenSuccessful(){
        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/add-author?book={title}&author={name}",
                                                                     HttpMethod.POST, null, BookDTO.class,
                          BOOK_TEST_NAME_IN_DB, AUTHOR_TEST_NAME_IN_DB);
        BookDTO bookDTO = exchange.getBody();

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(bookDTO).isNotNull();
        assertThat(bookDTO.getAuthors().contains(AUTHOR_TEST_NAME_IN_DB)).isTrue();
    }

    @Test
    public void addCategoryToABook_ShouldReturnBookUpdated_WhenSuccessful(){
        ResponseEntity<BookDTO> exchange = testRestTemplate
                .exchange("/api/v1/books/add-category?book={title}&category={name}",
                          HttpMethod.POST, null, BookDTO.class,
                          BOOK_TEST_NAME_IN_DB, CATEGORY_TEST_NAME_IN_DB);
        BookDTO bookDTO = exchange.getBody();

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(bookDTO).isNotNull();
        assertThat(bookDTO.getCategories().contains(CATEGORY_TEST_NAME_IN_DB)).isTrue();
    }
}
