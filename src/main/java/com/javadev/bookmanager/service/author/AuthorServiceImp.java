package com.javadev.bookmanager.service.author;

import com.javadev.bookmanager.dto.AuthorDTO;
import com.javadev.bookmanager.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository repository;

    private final ModelMapper mapper;

    @Override
    public List<AuthorDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(author -> mapper.map(author, AuthorDTO.class)).toList();
    }
}
