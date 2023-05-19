package com.javadev.bookmanager.service.book;

import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.entities.Book;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
import com.javadev.bookmanager.repository.BookRepository;
import com.javadev.bookmanager.request.BookPostRequestBody;
import com.javadev.bookmanager.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository repository;

    private final AuthorRepository authorRepository;

    @Override
    public List<BookDTO> listAll() {

        return repository.findAll().stream().map(BookDTO::new).toList();
    }

    @Override
    public BookDTO findById(long id) {
        return repository.findById(id).map(BookDTO::new)
                .orElseThrow(
                        () -> new BookNotFoundException("The book with id {" + id + "} wasn't found.")
                );
    }

    @Override
    public BookDTO findByName(String bookName) {
        return repository.findByNameIgnoreCase(bookName).map(BookDTO::new)
                .orElseThrow(() -> new BookNotFoundException("The book {" + bookName + "} wasn't found."));
    }

    @Override
    @Transactional
    public BookDTO insertAuthor(String bookName, String authorName) {
        Author author = authorRepository.findByNameIgnoreCase(authorName)
                .orElseThrow(() -> new AuthorNotFoundException("Author {" + authorName + "} wasn't found."));
        Book book = repository.findByNameIgnoreCase(bookName)
                .orElseThrow(() -> new BookNotFoundException("The book {" + bookName + "} wasn't found."));

        book.addAuthor(author);
        repository.save(book);

        return new BookDTO(book);
    }

    @Override
    @Transactional
    public BookDTO save(BookPostRequestBody bookRequestBody) {
        Book bookSaved = repository.save(bookRequestBody.transformToObject());
        return new BookDTO(bookSaved);
    }

    @Override
    @Transactional
    public BookDTO update(long id, BookPostRequestBody bookRequestBody) {
        Book bookToBeUpdated = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book with id {" + id + "} wasn't found."));

        bookToBeUpdated.setName(bookRequestBody.getName());
        bookToBeUpdated.setYear(bookRequestBody.getYear());
        bookToBeUpdated.setPages(bookRequestBody.getPages());

        repository.save(bookToBeUpdated);

        return new BookDTO(bookToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("The book with id {" + id + "} wasn't found."));
        repository.delete(book);
    }

}
