package com.javadev.bookmanager.service;

import com.javadev.bookmanager.dto.BookDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> listAll();
}
