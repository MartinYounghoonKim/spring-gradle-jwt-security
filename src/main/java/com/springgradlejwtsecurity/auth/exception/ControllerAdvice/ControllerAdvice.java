package com.springgradlejwtsecurity.auth.exception.ControllerAdvice;

import com.springgradlejwtsecurity.auth.dto.WebResponseDto;
import com.springgradlejwtsecurity.auth.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public WebResponseDto accountIdSigninFailedException (CustomException e) {
        return WebResponseDto.failure(e.getMessage());
    }
}
