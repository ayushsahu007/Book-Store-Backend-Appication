package com.bookstore.Springboot_BookStore.controller;


import com.bookstore.Springboot_BookStore.dto.LoginRequestDto;
import com.bookstore.Springboot_BookStore.dto.LoginResponseDto;
import com.bookstore.Springboot_BookStore.dto.SignupRequestDto;
import com.bookstore.Springboot_BookStore.dto.SignupResponseDto;
import com.bookstore.Springboot_BookStore.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto  signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

}
