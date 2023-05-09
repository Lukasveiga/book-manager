package com.javadev.bookmanager.repository;

import com.javadev.bookmanager.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
