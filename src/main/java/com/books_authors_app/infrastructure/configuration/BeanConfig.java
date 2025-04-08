package com.books_authors_app.infrastructure.configuration;

import com.books_authors_app.application.ports.in.AuthorUseCase;
import com.books_authors_app.application.ports.in.BookUseCase;
import com.books_authors_app.application.ports.out.AuthorRepository;
import com.books_authors_app.application.ports.out.BookRepository;
import com.books_authors_app.application.services.AuthorService;
import com.books_authors_app.application.services.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AuthorUseCase authorUseCase(AuthorRepository authorRepository) {
        return new AuthorService(authorRepository);
    }

    @Bean
    public BookUseCase bookUseCase(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }
}
