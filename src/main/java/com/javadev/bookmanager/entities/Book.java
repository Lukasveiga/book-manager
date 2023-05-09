package com.javadev.bookmanager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private String name;

    @Column(name = "book_year")
    private int year;

    private int pages;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "book_author",
    joinColumns = @JoinColumn(name = "id_book"),
    inverseJoinColumns = @JoinColumn(name = "id_author"))
    private List<Author> authors = new ArrayList<>();

    // private List<Category> categories;

    // private double price;

    // private boolean isAvailable;

    public Book(String name, int year, int pages) {
        this.name = name;
        this.year = year;
        this.pages = pages;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
