package com.javadev.bookmanager.dto;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private String name;

    private String about;

    private Set<String> books = new HashSet<>();

    public AuthorDTO(Author author) {
        this.name = author.getName();
        this.about = author.getAbout();

        this.books.addAll(
                author.getBooks().stream().map(Book::getName).toList());
    }

    public Author transformToObject() {
        return new Author(
                this.name,
                this.about
        );
    }
}
