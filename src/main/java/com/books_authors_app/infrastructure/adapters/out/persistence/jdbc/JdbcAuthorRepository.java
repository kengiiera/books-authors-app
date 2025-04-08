package com.books_authors_app.infrastructure.adapters.out.persistence.jdbc;

import com.books_authors_app.application.ports.out.AuthorRepository;
import com.books_authors_app.domain.entities.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author save(Author author) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_create_author")
                .declareParameters(
                        new SqlParameter("p_name", Types.VARCHAR),
                        new SqlOutParameter("p_author_id", Types.NUMERIC)
                );

        Map<String, Object> result = call.execute(author.getName());
        author.setId(((Number) result.get("p_author_id")).longValue());
        return author;
    }

    @Override
    public void update(Author author) {
        jdbcTemplate.update("BEGIN sp_update_author(?, ?); END;",
                author.getId(),
                author.getName()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("BEGIN sp_delete_author(?); END;", id);
    }

    @Override
    public Optional<Author> findById(Long id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * FROM authors WHERE author_id = ?", id);
        if (rs.next()) {
            Author author = new Author(rs.getLong("author_id"), rs.getString("name"));
            return Optional.of(author);
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM authors", (rs, rowNum) ->
                new Author(rs.getLong("author_id"), rs.getString("name"))
        );
    }
}
