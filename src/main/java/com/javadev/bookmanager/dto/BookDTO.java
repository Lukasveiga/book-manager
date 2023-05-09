package com.javadev.bookmanager.dto;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String name;

    private int year;

    private int pages;

    private List<String> authors;

    public BookDTO(Book book) {
        this.name = book.getName();
        this.year = book.getYear();
        this.pages = book.getPages();

        if (authors == null) {
            authors = new ArrayList<>();
        }

        this.authors.addAll(book.getAuthors()
                                    .stream()
                                    .map(Author::getName).toList());
    }
}
