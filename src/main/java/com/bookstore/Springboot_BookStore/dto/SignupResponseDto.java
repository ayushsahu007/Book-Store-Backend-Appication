package com.bookstore.Springboot_BookStore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {
    private Long id;
    private String username;
}
