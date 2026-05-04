package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class CartController {

    @Autowired
    private CartService  cartService;

    @PostMapping("/{userId}/cart")
    public Cart createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }
}
