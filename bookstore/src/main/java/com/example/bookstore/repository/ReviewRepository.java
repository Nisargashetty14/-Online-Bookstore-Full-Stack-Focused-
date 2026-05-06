package com.example.bookstore.repository;

import com.example.bookstore.model.Review;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository {

  private final JdbcTemplate jdbc;

  private static final RowMapper<Review> MAPPER =
      (ResultSet rs, int rowNum) -> {
        Review r = new Review();
        r.setId(rs.getLong("id"));
        r.setBookId(rs.getLong("book_id"));
        r.setAuthorName(rs.getString("author_name"));
        r.setComment(rs.getString("comment"));
        r.setRating(rs.getInt("rating"));
        Timestamp ts = rs.getTimestamp("created_at");
        r.setCreatedAt(ts != null ? ts.toInstant() : null);
        return r;
      };

  public ReviewRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Review> findByBookId(long bookId) {
    return jdbc.query(
        "SELECT id, book_id, author_name, comment, rating, created_at FROM reviews WHERE book_id = ? ORDER BY id DESC",
        MAPPER,
        bookId);
  }

  public Review insert(long bookId, String authorName, String comment, int rating) {
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(
        connection -> {
          var ps =
              connection.prepareStatement(
                  "INSERT INTO reviews (book_id, author_name, comment, rating) VALUES (?, ?, ?, ?)",
                  new String[] {"id"});
          ps.setLong(1, bookId);
          ps.setString(2, authorName);
          ps.setString(3, comment);
          ps.setInt(4, rating);
          return ps;
        },
        keyHolder);
    Number key = keyHolder.getKey();
    long id = key != null ? key.longValue() : -1L;
    Review r = new Review();
    r.setId(id);
    r.setBookId(bookId);
    r.setAuthorName(authorName);
    r.setComment(comment);
    r.setRating(rating);
    r.setCreatedAt(Instant.now());
    return r;
  }
}
