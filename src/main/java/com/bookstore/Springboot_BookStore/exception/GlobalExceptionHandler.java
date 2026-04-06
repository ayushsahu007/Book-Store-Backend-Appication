package com.bookstore.Springboot_BookStore.exception;

import com.bookstore.Springboot_BookStore.util.RestResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final RestResponseBuilder responseBuilder;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return responseBuilder.error(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        return responseBuilder.error(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {


        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return responseBuilder.validationError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(
            Exception ex,
            HttpServletRequest request) {

        return responseBuilder.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong",
                request
        );
    }
}