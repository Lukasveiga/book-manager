package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.request.BookPostRequestBody;
import com.javadev.bookmanager.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookPostRequestBody bookRequestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(bookRequestBody));
    }

    @PatchMapping
    public ResponseEntity<BookDTO> addAuthorToABook(@RequestParam("book") String bookName,
                                                    @RequestParam("author") String authorName) {
        return ResponseEntity.ok(service.insertAuthor(bookName, authorName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
