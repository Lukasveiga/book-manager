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

    @GetMapping("/{title}")
    public ResponseEntity<BookDTO> findByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookPostRequestBody bookRequestBody) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(bookRequestBody));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") long id,
                                              @RequestBody @Valid BookPostRequestBody bookRequestBody) {
        return ResponseEntity.ok(service.update(id, bookRequestBody));
    }

    @PostMapping("/add-author")
    public ResponseEntity<BookDTO> addAuthorToABook(@RequestParam("book") String bookTitle,
                                                    @RequestParam("author") String authorName) {
        return ResponseEntity.ok(service.insertAuthor(bookTitle, authorName));
    }

    @PostMapping("/add-category")
    public ResponseEntity<BookDTO> addCategoryToABook(@RequestParam("book") String bookTitle,
                                                    @RequestParam("category") String categoryName) {
        return ResponseEntity.ok(service.insertCategory(bookTitle, categoryName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
