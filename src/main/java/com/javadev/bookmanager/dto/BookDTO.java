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
public class BookDTO {

    private String name;

    private int year;

    private int pages;

    private Set<String> authors = new HashSet<>();

    public BookDTO(Book book) {
        this.name = book.getName();
        this.year = book.getYear();
        this.pages = book.getPages();
        this.authors.addAll(book.getAuthors()
                                    .stream()
                                    .map(Author::getName).toList());
    }

    public Book transformToObject() {
        return new Book(
                this.name,
                this.year,
                this.pages
        );
    }
}
