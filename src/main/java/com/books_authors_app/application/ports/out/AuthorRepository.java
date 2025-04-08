package com.books_authors_app.application.ports.out;

import com.books_authors_app.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    void update(Author author);

    void delete(Long id);

    Optional<Author> findById(Long id);

    List<Author> findAll();
}
