package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

  private final JdbcTemplate jdbc;

  private static final RowMapper<Book> MAPPER =
      (ResultSet rs, int rowNum) -> {
        Book b = new Book();
        b.setId(rs.getLong("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setDescription(rs.getString("description"));
        b.setPrice(rs.getBigDecimal("price"));
        b.setImageUrl(rs.getString("image_url"));
        return b;
      };

  public BookRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Book> findAll() {
    return jdbc.query("SELECT id, title, author, description, price, image_url FROM books ORDER BY id", MAPPER);
  }

  public List<Book> searchByTitle(String q) {
    String pattern = "%" + q.trim().toLowerCase() + "%";
    return jdbc.query(
        "SELECT id, title, author, description, price, image_url FROM books WHERE LOWER(title) LIKE ? ORDER BY id",
        MAPPER,
        pattern);
  }

  public Optional<Book> findById(long id) {
    List<Book> list =
        jdbc.query(
            "SELECT id, title, author, description, price, image_url FROM books WHERE id = ?",
            MAPPER,
            id);
    return list.stream().findFirst();
  }
}
