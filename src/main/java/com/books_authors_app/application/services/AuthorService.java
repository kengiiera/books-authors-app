package com.books_authors_app.application.services;

import com.books_authors_app.application.ports.in.AuthorUseCase;
import com.books_authors_app.application.ports.out.AuthorRepository;
import com.books_authors_app.domain.entities.Author;
import com.books_authors_app.domain.exceptions.CannotDeleteAuthorWithBooksException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

public class AuthorService implements AuthorUseCase {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author create(Author author) {
        return repository.save(author);
    }

    @Override
    public void update(Author author) {
        repository.update(author);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.delete(id);
        } catch (DataIntegrityViolationException ex) {
            throw new CannotDeleteAuthorWithBooksException();
        }
    }

    @Override
    public Optional<Author> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }
}
