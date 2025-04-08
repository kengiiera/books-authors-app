package com.books_authors_app.infrastructure.adapters.out.persistence.jdbc;

import com.books_authors_app.application.ports.out.BookRepository;
import com.books_authors_app.domain.entities.Author;
import com.books_authors_app.domain.entities.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("book_id"));
        book.setTitle(rs.getString("title"));
        book.setPublicationDate(Optional.ofNullable(rs.getDate("publication_date"))
                .map(Date::toLocalDate).orElse(null));
        book.setIsbn(rs.getString("isbn"));
        book.setPages(rs.getInt("pages"));

        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setName(rs.getString("author_name"));
        book.setAuthor(author);

        return book;
    };

    @Override
    public Book save(Book book) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_create_book")
                .declareParameters(
                        new SqlParameter("p_title", Types.VARCHAR),
                        new SqlParameter("p_publication_date", Types.DATE),
                        new SqlParameter("p_isbn", Types.VARCHAR),
                        new SqlParameter("p_pages", Types.INTEGER),
                        new SqlParameter("p_author_id", Types.NUMERIC),
                        new SqlOutParameter("p_book_id", Types.NUMERIC)
                );

        Map<String, Object> result = call.execute(
                book.getTitle(),
                Date.valueOf(book.getPublicationDate()),
                book.getIsbn(),
                book.getPages(),
                book.getAuthor().getId()
        );

        book.setId(((Number) result.get("p_book_id")).longValue());
        return book;
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("BEGIN sp_update_book(?, ?, ?, ?, ?, ?); END;",
                book.getId(),
                book.getTitle(),
                Date.valueOf(book.getPublicationDate()),
                book.getIsbn(),
                book.getPages(),
                book.getAuthor().getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("BEGIN sp_delete_book(?); END;", id);
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = """
            SELECT b.*, a.name AS author_name
            FROM books b
            JOIN authors a ON b.author_id = a.author_id
            WHERE b.book_id = ?
        """;
        try {
            Book book = jdbcTemplate.queryForObject(sql, new Object[]{id}, bookRowMapper);
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = """
            SELECT b.*, a.name AS author_name
            FROM books b
            JOIN authors a ON b.author_id = a.author_id
        """;
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public List<Book> search(String title, Long authorId, LocalDate publicationDate) {
        StringBuilder sql = new StringBuilder("""
            SELECT b.*, a.name AS author_name
            FROM books b
            JOIN authors a ON b.author_id = a.author_id
        """);

        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (title != null) {
            conditions.add("LOWER(b.title) LIKE ?");
            params.add("%" + title.toLowerCase() + "%");
        }
        if (authorId != null) {
            conditions.add("b.author_id = ?");
            params.add(authorId);
        }
        if (publicationDate != null) {
            conditions.add("b.publication_date = ?");
            params.add(Date.valueOf(publicationDate));
        }
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), bookRowMapper);
    }
}
