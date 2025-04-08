package com.books_authors_app.application.ports.in;

import com.books_authors_app.domain.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorUseCase {
    Author create(Author author);

    void update(Author author);

    void delete(Long id);

    Optional<Author> getById(Long id);

    List<Author> getAll();
}
