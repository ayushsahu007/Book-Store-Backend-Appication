package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.OrderRequestDTO;
import com.bookstore.Springboot_BookStore.dto.OrderResponseDTO;
import com.bookstore.Springboot_BookStore.model.Order;
import com.bookstore.Springboot_BookStore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public OrderResponseDTO placeOrder(@RequestBody OrderRequestDTO requestDTO) {
        return orderService.placeOrder(requestDTO);
    }
}
