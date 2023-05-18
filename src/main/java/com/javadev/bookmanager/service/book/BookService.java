package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Book;

import java.util.List;

public interface BookService {

    List<BookDTO> listAll();

    BookDTO save(BookDTO bookDTO);

    BookDTO insertAuthor(String bookName, String authorName);

    Book findByName(String name);
}
