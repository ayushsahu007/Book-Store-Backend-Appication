package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService  cartService;

    @PostMapping("/cart/{userId}")
    public Cart createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }
}
