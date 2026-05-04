package com.bookstore.Springboot_BookStore.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long userId;
    private Long cartId;
}
