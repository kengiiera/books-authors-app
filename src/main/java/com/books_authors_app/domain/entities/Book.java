package com.books_authors_app.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String isbn;
    private Integer pages;
    private Author author;
}
