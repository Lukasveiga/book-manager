package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.request.AuthorRequestBody;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> listAll();

    List<BookDTO> listAllBooksByAuthor(String authorName);

    AuthorDTO save(AuthorRequestBody authorRequestBody);

    AuthorDTO findByName(String name);

    AuthorDTO findById(long id);

    AuthorDTO update(long id, AuthorRequestBody authorRequestBody);

    void delete(long id);
}
