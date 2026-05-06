package com.example.bookstore.dto;

import com.example.bookstore.model.CartLine;
import java.math.BigDecimal;
import java.util.List;

public class CartResponse {

  private List<CartLine> items;
  private BigDecimal total;

  public CartResponse(List<CartLine> items, BigDecimal total) {
    this.items = items;
    this.total = total;
  }

  public List<CartLine> getItems() {
    return items;
  }

  public void setItems(List<CartLine> items) {
    this.items = items;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
