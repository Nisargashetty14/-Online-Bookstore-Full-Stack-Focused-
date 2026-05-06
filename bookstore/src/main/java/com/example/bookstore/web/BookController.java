package com.example.bookstore.web;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookRepository books;

  public BookController(BookRepository books) {
    this.books = books;
  }

  @GetMapping
  public List<Book> list(@RequestParam(required = false) String q) {
    if (q != null && !q.isBlank()) {
      return books.searchByTitle(q);
    }
    return books.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> get(@PathVariable long id) {
    return books.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
