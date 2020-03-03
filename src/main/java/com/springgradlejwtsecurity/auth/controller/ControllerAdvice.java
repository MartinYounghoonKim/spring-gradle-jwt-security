package com.springgradlejwtsecurity.auth.controller;

import com.springgradlejwtsecurity.auth.exception.AccountIdSigninFailedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(AccountIdSigninFailedException.class)
    public String accountIdSigninFailedException (AccountIdSigninFailedException e) {
        return e.getMessage();
    }
}
