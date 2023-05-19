package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.util.GenerateBookAndAuthor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    private Author author;

    @BeforeEach
    void setup() {
        author = GenerateBookAndAuthor.generateAuthorTest();
        repository.save(author);
    }

    @AfterEach
    void tearDown() {
        author = null;
        this.repository.deleteAll();
    }

    @Test
    void findByNameIgnoreCase_returnAuthorByName_WhenSuccessful(){
        Author authorTest = repository.findByNameIgnoreCase(author.getName()).orElse(new Author());
        Assertions
                .assertThat(authorTest)
                .isNotNull()
                .isEqualTo(author);
    }

    @Test
    void findByNameIgnoreCase_ThrowAuthorNotFoundException_WhenAuthorIsNotFound(){
        String authorNameTest = "Author Test Exception";
        Assertions.assertThatExceptionOfType(AuthorNotFoundException.class)
                .isThrownBy(() -> {
                    repository
                            .findByNameIgnoreCase(authorNameTest)
                            .orElseThrow(() -> new AuthorNotFoundException(""));
                });
    }
}