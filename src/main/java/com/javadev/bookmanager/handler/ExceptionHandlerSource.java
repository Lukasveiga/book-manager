package com.javadev.bookmanager.handler;

import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.exceptions.ExceptionDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerSource {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ExceptionDetails> authorNotFound(AuthorNotFoundException ex, HttpServletRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionDetails> bookNotFound(BookNotFoundException ex, HttpServletRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }
}
