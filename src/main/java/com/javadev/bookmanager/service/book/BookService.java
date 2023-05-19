package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.request.BookRequestBody;

import java.util.List;

public interface BookService {

    List<BookDTO> listAll();

    BookDTO save(BookRequestBody bookRequestBody);

    BookDTO insertAuthor(String bookName, String authorName);

    BookDTO findByName(String name);

    public BookDTO findById(long id);

    BookDTO update(long id, BookRequestBody bookRequestBody);

    void delete(long id);
}
