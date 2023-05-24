package com.javadev.bookmanager.integration;

import com.javadev.bookmanager.AbstractPostgresTestContainer;
import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.repository.AuthorRepository;
import com.javadev.bookmanager.util.GenerateBookAuthorCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerIT extends AbstractPostgresTestContainer {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository repository;

    @Test
    public void findAll_ReturnListOfAuthors_WhenSuccessful() {
        Author authorSaved = repository.save(GenerateBookAuthorCategory.generateAuthorTest());

        List<AuthorDTO> authors = testRestTemplate.
                exchange("/api/v1/authors", HttpMethod.GET, null,
                         new ParameterizedTypeReference<List<AuthorDTO>>() {
        }).getBody();
        assertThat(authors).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authors.get(0)).usingRecursiveComparison().isEqualTo(new AuthorDTO(authorSaved));
    }



}
