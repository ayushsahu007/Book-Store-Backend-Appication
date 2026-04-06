package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.model.Book;
import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.CartItem;
import com.bookstore.Springboot_BookStore.repository.BookRepository;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Cart addItem(Long cartId, Long bookId, Integer quantity) {

        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (quantity > book.getStock()) {
            throw new RuntimeException("Only " + book.getStock() + " books available");
        }

        // existing cartItem check
        CartItem existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int totalQty = existingItem.getQuantity() + quantity;
            if (totalQty > book.getStock()) {
                throw new RuntimeException("Only " + book.getStock() + " books available in total");
            }
            existingItem.setQuantity(totalQty);
            existingItem.setPrice(totalQty * book.getPrice());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(quantity * book.getPrice());
            cart.getCartItems().add(cartItem);
        }

        // update cart total
        double total = cart.getCartItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        cart.setTotalPrice(total);

        return cartRepository.save(cart);
    }
}
