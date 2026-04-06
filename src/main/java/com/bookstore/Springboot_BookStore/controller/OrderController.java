package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.model.Order;
import com.bookstore.Springboot_BookStore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestParam Long userId, @RequestParam Long cartId) {
        return orderService.placeOrder(userId, cartId);
    }
}
