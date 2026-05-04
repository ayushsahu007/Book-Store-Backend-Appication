package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.CartItemRequestDTO;
import com.bookstore.Springboot_BookStore.dto.CartResponseDTO;
import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/{cartId}/items")
    public CartResponseDTO addToCart(@PathVariable Long cartId, @RequestBody CartItemRequestDTO request) {
        return cartItemService.addItem(cartId,request);
    }

}
