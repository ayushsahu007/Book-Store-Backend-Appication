package com.bookstore.Springboot_BookStore.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long itemId;
    private Long bookId;
    private String bookName;

    private Integer quantity;
    private Double price;

    private Double totalPrice;

}
