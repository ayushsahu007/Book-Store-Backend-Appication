package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.CartResponseDTO;
import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CartController {


    private final CartService  cartService;

    @PostMapping("/{userId}/cart")
    public CartResponseDTO createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    @GetMapping("/{userId}/cart")
    public CartResponseDTO getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }
}
