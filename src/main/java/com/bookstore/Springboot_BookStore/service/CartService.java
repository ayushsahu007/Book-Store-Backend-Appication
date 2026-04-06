package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import com.bookstore.Springboot_BookStore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart createCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ check if cart already exists
        Cart existingCart = cartRepository.findByUserId(userId);
        if (existingCart != null) {
            return existingCart;
        }

        // ✅ create new cart
        Cart cart = new Cart();
        cart.setUser(user);

        // ✅ set both sides (if bidirectional)
        user.setCart(cart);

        return cartRepository.save(cart);
    }
}
