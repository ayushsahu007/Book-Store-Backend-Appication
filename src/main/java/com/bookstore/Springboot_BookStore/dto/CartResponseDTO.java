package com.bookstore.Springboot_BookStore.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {
    private Long cartId;
    private Long userId;

    private List<CartItemResponseDTO> items;

    private Double totalPrice;
}
