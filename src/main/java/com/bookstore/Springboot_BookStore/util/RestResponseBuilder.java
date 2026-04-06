package com.bookstore.Springboot_BookStore.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RestResponseBuilder {

    public <T> ResponseEntity<ApiResponse<T>> success(
            HttpStatus status,
            String message,
            T data,
            HttpServletRequest request) {

        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(true)
                        .message(message)
                        .data(data)
                        .path(request.getRequestURI())
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<Object>> error(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(false)
                        .message(message)
                        .path(request.getRequestURI())
                        .build()
        );
    }

    public ResponseEntity<ApiResponse<Object>> validationError(
            HttpStatus status,
            String message,
            Map<String, String> errors,
            HttpServletRequest request) {

        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(false)
                        .message(message)
                        .errors(errors)
                        .path(request.getRequestURI())
                        .build()
        );
    }
}
