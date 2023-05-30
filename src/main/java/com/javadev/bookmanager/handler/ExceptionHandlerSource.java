package com.javadev.bookmanager.handler;

import com.javadev.bookmanager.exceptions.AuthorNotFoundException;
import com.javadev.bookmanager.exceptions.BookNotFoundException;
import com.javadev.bookmanager.exceptions.CategoryNotFoundException;
import com.javadev.bookmanager.exceptions.ExceptionDetailsBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerSource extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ExceptionDetailsBody> authorNotFound(AuthorNotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                ExceptionDetailsBody
                        .builder()
                        .path(request.getRequestURI())
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .localDateTime(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionDetailsBody> bookNotFound(BookNotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                ExceptionDetailsBody
                        .builder()
                        .path(request.getRequestURI())
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .localDateTime(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDetailsBody> CategoryNotFound(CategoryNotFoundException ex, HttpServletRequest request) {

        return new ResponseEntity<>(
                ExceptionDetailsBody
                        .builder()
                        .path(request.getRequestURI())
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .localDateTime(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        return new ResponseEntity<>(ExceptionDetailsBody
                                            .builder()
                                            .path(((ServletWebRequest)request).getRequest().getRequestURI())
                                            .message(fieldsMessage)
                                            .statusCode(status.value())
                                            .localDateTime(LocalDateTime.now())
                                            .build(), HttpStatus.BAD_REQUEST);
    }
}
