package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainingIgnoreCase(String name);
}
