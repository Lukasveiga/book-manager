package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
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
    public List<Book> findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        repository.save(bookDTO.transformToObject());
        return bookDTO;
    }

    @Override
    public BookDTO insertAuthor(String bookName, String authorName) {
        List<Author> authorList = authorService.findByName(authorName);
        List<Book> bookList = findByName(bookName);

        if (authorList.isEmpty()) {
            throw new AuthorNotFoundException(authorName + " wasn't found.");
        } else if (bookList.isEmpty()) {
            throw new BookNotFoundException(bookName + " wasn't found.");
        }

        Author author = authorList.get(0);
        Book book = bookList.get(0);

        book.addAuthor(author);
        repository.save(book);

        return new BookDTO(book);
    }


}
