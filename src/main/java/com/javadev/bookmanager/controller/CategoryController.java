package com.javadev.bookmanager.controller;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.dto.CategoryDTO;
import com.javadev.bookmanager.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/{category}/books")
    public ResponseEntity<List<BookDTO>> findAllBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.listAllBooksByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") long id,
                                                  @RequestBody @Valid CategoryDTO categoryDTO) {
        return ResponseEntity.ok(service.update(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
