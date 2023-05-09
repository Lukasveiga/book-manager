package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> listAll();

    List<BookDTO> listAllBooksByAuthor(String authorName);

    AuthorDTO save(AuthorDTO authorDTO);

    List<Author> findByName(String name);
}
