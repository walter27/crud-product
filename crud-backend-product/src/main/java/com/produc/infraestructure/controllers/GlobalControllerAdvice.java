package com.produc.infraestructure.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.produc.domain.exceptions.ProductException;
import com.produc.infraestructure.models.ErrorDto;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorDto> handleProductException(ProductException exception) {
        ErrorDto error = ErrorDto.builder()
                .code("PRODUCT_NOT_FOUND")
                .message(exception.getMessage())
                .details(List.of())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .toList();

        ErrorDto error = ErrorDto.builder()
                .code("VALIDATION_ERROR")
                .message("Request validation failed")
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /*@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException exception) {
        ErrorDto error = ErrorDto.builder()
                .code("UNAUTHORIZED")
                .message(exception.getMessage())
                .details(List.of())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorDto error = ErrorDto.builder()
                .code("FORBIDDEN")
                .message(exception.getMessage())
                .details(List.of())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }*/

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
