package com.bookstore.Springboot_BookStore.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Long userId;

    private List<OrderItemResponseDTO> items;

    private Double totalAmount;

    private String status; // PLACED, SHIPPED, DELIVERED

    private LocalDateTime orderDate;
}
