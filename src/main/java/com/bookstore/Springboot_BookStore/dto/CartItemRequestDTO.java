package com.bookstore.Springboot_BookStore.dto;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Long bookId;
    private Integer quantity;
}

