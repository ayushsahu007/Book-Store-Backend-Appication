package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/cart/{cartId}/add")
    public Cart addToCart(@PathVariable Long cartId, @RequestParam Long bookId,@RequestParam Integer quantity) {
        return cartItemService.addItem(cartId,bookId,quantity);
    }

}
