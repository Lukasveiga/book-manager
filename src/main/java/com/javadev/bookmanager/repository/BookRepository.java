package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleIgnoreCase(String name);

    List<Book> findByIsAvailableTrue();
}
