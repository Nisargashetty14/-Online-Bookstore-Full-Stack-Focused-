package com.example.bookstore.web;

import com.example.bookstore.dto.CheckoutResponse;
import com.example.bookstore.model.CartLine;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

  @PostMapping
  public ResponseEntity<?> checkout(HttpSession session) {
    @SuppressWarnings("unchecked")
    List<CartLine> cart =
        (List<CartLine>) session.getAttribute(CartController.SESSION_CART_KEY);
    if (cart == null || cart.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new CheckoutResponse("Your cart is empty.", null));
    }
    String orderId = UUID.randomUUID().toString();
    session.setAttribute(CartController.SESSION_CART_KEY, new ArrayList<CartLine>());
    return ResponseEntity.ok(
        new CheckoutResponse("Thank you! Your order has been placed.", orderId));
  }
}
