package com.springgradlejwtsecurity.auth.exception.ControllerAdvice;

import com.springgradlejwtsecurity.auth.dto.WebResponseDto;
import com.springgradlejwtsecurity.auth.exception.CustomException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public WebResponseDto customExceptionHandler (CustomException e) {
        return WebResponseDto.failure(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResponseDto methodArgumentNotValidExceptionHandler (MethodArgumentNotValidException e) {
        return WebResponseDto.failure(e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
