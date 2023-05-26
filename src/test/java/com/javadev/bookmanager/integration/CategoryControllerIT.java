package com.javadev.bookmanager.integration;

import com.javadev.bookmanager.AbstractPostgresTestContainer;
import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.javadev.bookmanager.util.EntitiesInformationDatabaseTest.BOOK_TEST_NAME_IN_DB;
import static com.javadev.bookmanager.util.EntitiesInformationDatabaseTest.CATEGORY_TEST_NAME_IN_DB;
import static com.javadev.bookmanager.util.GenerateBookAuthorCategory.generateCategoryDTO;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryControllerIT  extends AbstractPostgresTestContainer {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void findAll_ReturnListOfCategories_WhenSuccessful() {
        ResponseEntity<List<CategoryDTO>> exchange = testRestTemplate.
                exchange("/api/v1/categories", HttpMethod.GET, null,
                         new ParameterizedTypeReference<>() {
                         });

        List<CategoryDTO> categories = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categories).isNotNull().isNotEmpty().hasSize(1);
        assertThat(categories.get(0).getName()).isEqualTo(CATEGORY_TEST_NAME_IN_DB);
    }

    @Test
    public void findByName_ReturnCategoryByName_WhenSuccessful() {
        ResponseEntity<CategoryDTO> exchange = testRestTemplate
                .exchange("/api/v1/categories/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, CATEGORY_TEST_NAME_IN_DB);

        CategoryDTO category = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo(CATEGORY_TEST_NAME_IN_DB);
    }

    @Test
    public void findByName_Return404NotFound_WhenCategoryIsNotFoundByName() {
        ResponseEntity<AuthorDTO> exchange = testRestTemplate
                .exchange("/api/v1/categories/{name}", HttpMethod.GET, null,
                          new ParameterizedTypeReference<>() {
                          }, "");

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findAllBooksByCategory_ReturnListOfBooksByCategory_WhenSuccessful() {
        ResponseEntity<List<BookDTO>> exchange = testRestTemplate.
                exchange("/api/v1/categories/{name}/books", HttpMethod.GET, null,
                         new ParameterizedTypeReference<>() {

                         }, CATEGORY_TEST_NAME_IN_DB);
        List<BookDTO> books = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo(BOOK_TEST_NAME_IN_DB);
    }

    @Test
    public void addCategory_ShouldSaveAndReturnSavedCategory_WhenSuccessful(){
        CategoryDTO categoryRequest = generateCategoryDTO();

        ResponseEntity<CategoryDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/categories", categoryRequest, CategoryDTO.class);

        CategoryDTO categorySaved = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(categorySaved).isNotNull();
        assertThat(categorySaved.getName()).isEqualTo(categoryRequest.getName());
    }

    @Test
    public void addCategory_Should400BadRequest_WhenPassEmptyParam(){
        CategoryDTO categoryRequest = generateCategoryDTO();
        categoryRequest.setName("");

        ResponseEntity<CategoryDTO> exchange = testRestTemplate
                .postForEntity("/api/v1/categories", categoryRequest, CategoryDTO.class);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateCategory_ShouldUpdateAndReturnCategoryUpdatedAuthor_WhenSuccessful(){
        CategoryDTO categoryRequest = generateCategoryDTO();
        categoryRequest.setName(CATEGORY_TEST_NAME_IN_DB);
        long id = 1;

        ResponseEntity<CategoryDTO> exchange = testRestTemplate
                .exchange("/api/v1/categories/{id}", HttpMethod.PUT,
                          new HttpEntity<>(categoryRequest), CategoryDTO.class, id);

        CategoryDTO categoryDTO = exchange.getBody();

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(categoryDTO).isNotNull();
        assertThat(categoryDTO.getName()).isEqualTo(categoryRequest.getName());
    }

    @Test
    public void updateCategory_Return404NotFound_WhenCategoryIsNotFoundById() {
        CategoryDTO categoryRequest = generateCategoryDTO();
        long id = 5;

        ResponseEntity<CategoryDTO> exchange = testRestTemplate
                .exchange("/api/v1/categories/{id}", HttpMethod.PUT,
                          new HttpEntity<>(categoryRequest), CategoryDTO.class, id);

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteCategory_ShouldDeleteCategoryAndReturnVoid_WhenSuccessful() {
        long id = 1;

        ResponseEntity<Void> exchange = testRestTemplate.
                exchange("/api/v1/categories/{id}", HttpMethod.DELETE, null, Void.class, id);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
