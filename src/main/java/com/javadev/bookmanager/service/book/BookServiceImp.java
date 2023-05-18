package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.repository.BookRepository;
import com.javadev.bookmanager.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository repository;

    private final AuthorService authorService;

    @Override
    public List<BookDTO> listAll() {

        return repository.findAll().stream().map(BookDTO::new).toList();
    }

    @Override
    public Book findByName(String name) {
        return repository.findByNameIgnoreCase(name).orElseThrow(() -> new BookNotFoundException("The book {" + name + "} wasn't found."));
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        repository.save(bookDTO.transformToObject());
        return bookDTO;
    }

    @Override
    public BookDTO insertAuthor(String bookName, String authorName) {
        Author author = authorService.findByName(authorName);
        Book book = findByName(bookName);

        book.addAuthor(author);
        repository.save(book);

        return new BookDTO(book);
    }


}
