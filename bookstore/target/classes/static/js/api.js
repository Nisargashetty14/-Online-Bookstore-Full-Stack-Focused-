const api = {
  opts() {
    return { credentials: "include", headers: { "Content-Type": "application/json" } };
  },

  async books(q) {
    const url = q ? `/books?q=${encodeURIComponent(q)}` : "/books";
    const r = await fetch(url, { credentials: "include" });
    if (!r.ok) throw new Error("Failed to load books");
    return r.json();
  },

  async book(id) {
    const r = await fetch(`/books/${id}`, { credentials: "include" });
    if (r.status === 404) return null;
    if (!r.ok) throw new Error("Failed to load book");
    return r.json();
  },

  async reviews(bookId) {
    const r = await fetch(`/books/${bookId}/reviews`, { credentials: "include" });
    if (r.status === 404) return null;
    if (!r.ok) throw new Error("Failed to load reviews");
    return r.json();
  },

  async addReview(bookId, body) {
    const r = await fetch(`/books/${bookId}/reviews`, {
      method: "POST",
      ...this.opts(),
      body: JSON.stringify(body),
    });
    if (!r.ok) throw new Error("Could not post review");
    return r.json();
  },

  async addToCart(bookId, quantity = 1) {
    const r = await fetch("/cart", {
      method: "POST",
      ...this.opts(),
      body: JSON.stringify({ bookId, quantity }),
    });
    if (!r.ok) throw new Error("Could not add to cart");
    return r.json();
  },

  async cart() {
    const r = await fetch("/cart", { credentials: "include" });
    if (!r.ok) throw new Error("Cart error");
    return r.json();
  },

  async removeFromCart(bookId) {
    const r = await fetch(`/cart/${bookId}`, { method: "DELETE", credentials: "include" });
    if (!r.ok) throw new Error("Remove failed");
    return r.json();
  },

  async checkout() {
    const r = await fetch("/checkout", { method: "POST", credentials: "include" });
    const data = await r.json();
    if (!r.ok) return { ok: false, ...data };
    return { ok: true, ...data };
  },
};

function money(n) {
  const x = typeof n === "number" ? n : parseFloat(n);
  if (Number.isNaN(x)) return "—";
  return new Intl.NumberFormat(undefined, { style: "currency", currency: "USD" }).format(x);
}
