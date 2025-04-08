package com.books_authors_app.infrastructure.adapters.in.rest;


import com.books_authors_app.domain.exceptions.CannotDeleteAuthorWithBooksException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotDeleteAuthorWithBooksException.class)
    public ResponseEntity<Map<String, String>> handleAuthorDeletion(CannotDeleteAuthorWithBooksException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
