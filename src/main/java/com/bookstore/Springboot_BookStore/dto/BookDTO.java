package com.bookstore.Springboot_BookStore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    @NotBlank(message = "Title is required")
    private  String title;

    @NotBlank(message = "Author is required")
    private String author;

    private Double price;
    private Integer stock;

}
