package com.javadev.bookmanager.dto;

import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.entities.Category;
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

    private String title;

    private int year;

    private int pages;

    private String language;

    private String image;

    private boolean isAvailable;

    private Set<String> authors = new HashSet<>();

    private Set<String> categories = new HashSet<>();

    public BookDTO(Book book) {
        this.title = book.getTitle();
        this.year = book.getYear();
        this.pages = book.getPages();
        this.language = book.getLanguage();
        this.image = book.getImage();
        this.isAvailable = book.isAvailable();
        this.authors.addAll(book.getAuthors()
                                    .stream()
                                    .map(Author::getName).toList());
        this.categories.addAll(book.getCategories()
                                       .stream()
                                       .map(Category::getName).toList());
    }
}
