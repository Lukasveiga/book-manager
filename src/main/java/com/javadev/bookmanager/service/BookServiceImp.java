package com.javadev.bookmanager.service;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository repository;

    private final ModelMapper mapper;

    @Override
    public List<BookDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(book -> mapper.map(book, BookDTO.class)).toList();
    }
}
