package com.springgradlejwtsecurity.auth.controller;

import com.springgradlejwtsecurity.auth.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public String accountIdSigninFailedException (CustomException e) {
        return e.getMessage();
    }
}
