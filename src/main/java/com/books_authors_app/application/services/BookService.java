package com.books_authors_app.application.services;

import com.books_authors_app.application.ports.in.BookUseCase;
import com.books_authors_app.application.ports.out.BookRepository;
import com.books_authors_app.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookService implements BookUseCase {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public void update(Book book) {
        repository.update(book);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> search(String title, Long authorId, LocalDate publicationDate) {
        return repository.search(title, authorId, publicationDate);
    }
}