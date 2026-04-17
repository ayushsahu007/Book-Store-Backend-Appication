package com.bookstore.Springboot_BookStore.security;

import com.bookstore.Springboot_BookStore.dto.LoginRequestDto;
import com.bookstore.Springboot_BookStore.dto.LoginResponseDto;
import com.bookstore.Springboot_BookStore.dto.SignupRequestDto;
import com.bookstore.Springboot_BookStore.dto.SignupResponseDto;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.model.UserPrincipal;
import com.bookstore.Springboot_BookStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(userPrincipal);

        return new LoginResponseDto(token, userPrincipal.getId());
    }

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        Optional<User> existingUser = userRepository.findByUsername(signupRequestDto.getUsername());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User Already Exists");
        }

      User user = userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .email(signupRequestDto.getEmail())
                .role(signupRequestDto.getRole())
                .build()
        );

        return new SignupResponseDto(user.getId(), user.getUsername());

    }


}
