package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> listAll();
}
