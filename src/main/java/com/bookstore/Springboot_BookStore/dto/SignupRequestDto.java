package com.bookstore.Springboot_BookStore.dto;

import com.bookstore.Springboot_BookStore.model.type.Role;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private Role role; // "USER" or "ADMIN"
}
