package com.javadev.bookmanager.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
