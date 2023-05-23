package com.javadev.bookmanager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "book_year")
    private int year;

    @Column(name = "pages")
    private int pages;

    @Column(name = "language")
    private String language;

    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "book_author",
    joinColumns = @JoinColumn(name = "id_book"),
    inverseJoinColumns = @JoinColumn(name = "id_author"))
    private Set<Author> authors = new HashSet<>();

    // private List<Category> categories;

    // private double price;

    // private boolean isAvailable;

    public Book(String name, int year, int pages, String language, String image) {
        this.title = name;
        this.year = year;
        this.pages = pages;
        this.language = language;
        this.image = image;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Book book = (Book) o;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
