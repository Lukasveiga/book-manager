package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.service.author.AuthorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{author}/books")
    public ResponseEntity<List<BookDTO>> findAllBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(service.listAllBooksByAuthor(author));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(authorDTO));
    }
}
