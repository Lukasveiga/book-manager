package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
