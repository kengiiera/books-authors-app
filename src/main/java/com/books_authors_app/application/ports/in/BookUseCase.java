package com.books_authors_app.application.ports.in;

import com.books_authors_app.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookUseCase {
    Book create(Book book);

    void update(Book book);

    void delete(Long id);

    Optional<Book> getById(Long id);

    List<Book> getAll();

    List<Book> search(String title, Long authorId, LocalDate publicationDate);
}
