package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.dto.BookDTO;
import com.javadev.bookmanager.entities.Author;
import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AuthorDTO findById(Long id) {
        return repository.findById(id).map(AuthorDTO::new).orElseThrow(
                        () -> new AuthorNotFoundException("Author  with id {" + id + "} wasn't found."));
    }

    @Override
    public AuthorDTO findByName(String name) {
        return repository.findByNameIgnoreCase(name).map(AuthorDTO::new)
                .orElseThrow(() -> new AuthorNotFoundException("Author {" + name + "} wasn't found."));
    }

    @Override
    public List<BookDTO> listAllBooksByAuthor(String name) {
        Author author = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new AuthorNotFoundException("Author {" + name + "} wasn't found."));
        return author.getBooks()
                .stream().map(BookDTO::new).toList();
    }

    @Override
    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        repository.save(authorDTO.transformToObject());
        return authorDTO;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author  with id {" + id + "} wasn't found."));

        repository.delete(author);
    }

}
