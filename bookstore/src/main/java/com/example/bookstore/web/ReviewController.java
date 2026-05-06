package com.example.bookstore.web;

import com.example.bookstore.dto.CreateReviewRequest;
import com.example.bookstore.model.Review;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ReviewRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

  private final BookRepository books;
  private final ReviewRepository reviews;

  public ReviewController(BookRepository books, ReviewRepository reviews) {
    this.books = books;
    this.reviews = reviews;
  }

  @GetMapping
  public ResponseEntity<List<Review>> list(@PathVariable long bookId) {
    if (books.findById(bookId).isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(reviews.findByBookId(bookId));
  }

  @PostMapping
  public ResponseEntity<Review> create(
      @PathVariable long bookId, @Valid @RequestBody CreateReviewRequest body) {
    if (books.findById(bookId).isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Review saved =
        reviews.insert(bookId, body.getAuthorName(), body.getComment(), body.getRating());
    return ResponseEntity.ok(saved);
  }
}
