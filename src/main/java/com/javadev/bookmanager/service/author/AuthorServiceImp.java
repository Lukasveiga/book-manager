package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository repository;

    @Override
    public List<AuthorDTO> listAll() {
        return repository.findAll().stream().map(AuthorDTO::new).toList();
    }

    @Override
    public Author findByName(String name) {
        return repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new AuthorNotFoundException("Author {" + name + "} wasn't found."));
    }

    @Override
    public List<BookDTO> listAllBooksByAuthor(String authorName) {
        return findByName(authorName)
                .getBooks()
                .stream().map(BookDTO::new).toList();
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        repository.save(authorDTO.transformToObject());
        return authorDTO;
    }


}
