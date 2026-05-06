package com.example.bookstore.web;

import com.example.bookstore.dto.AddToCartRequest;
import com.example.bookstore.dto.CartResponse;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartLine;
import com.example.bookstore.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  public static final String SESSION_CART_KEY = "BOOKSTORE_CART";

  private final BookRepository books;

  public CartController(BookRepository books) {
    this.books = books;
  }

  @SuppressWarnings("unchecked")
  private List<CartLine> cart(HttpSession session) {
    Object raw = session.getAttribute(SESSION_CART_KEY);
    if (raw instanceof List) {
      return (List<CartLine>) raw;
    }
    List<CartLine> created = new ArrayList<>();
    session.setAttribute(SESSION_CART_KEY, created);
    return created;
  }

  @PostMapping
  public ResponseEntity<CartResponse> add(
      @Valid @RequestBody AddToCartRequest req, HttpSession session) {
    Book book = books.findById(req.getBookId()).orElse(null);
    if (book == null) {
      return ResponseEntity.notFound().build();
    }
    List<CartLine> cart = cart(session);
    CartLine existing =
        cart.stream()
            .filter(l -> l.getBookId() == book.getId().longValue())
            .findFirst()
            .orElse(null);
    int addQty = req.getQuantity();
    if (existing != null) {
      existing.setQuantity(existing.getQuantity() + addQty);
    } else {
      CartLine line = new CartLine();
      line.setBookId(book.getId().longValue());
      line.setTitle(book.getTitle());
      line.setAuthor(book.getAuthor());
      line.setUnitPrice(book.getPrice());
      line.setQuantity(addQty);
      cart.add(line);
    }
    return ResponseEntity.ok(buildResponse(cart));
  }

  @GetMapping
  public CartResponse get(HttpSession session) {
    return buildResponse(cart(session));
  }

  @DeleteMapping("/{bookId}")
  public ResponseEntity<CartResponse> remove(@PathVariable long bookId, HttpSession session) {
    List<CartLine> cart = cart(session);
    cart.removeIf(l -> l.getBookId() == bookId);
    return ResponseEntity.ok(buildResponse(cart));
  }

  private static CartResponse buildResponse(List<CartLine> cart) {
    BigDecimal total =
        cart.stream()
            .map(CartLine::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    return new CartResponse(List.copyOf(cart), total);
  }
}
