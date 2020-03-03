package com.springgradlejwtsecurity.auth.exception;

public class AccountIdSigninFailedException extends RuntimeException {
    private String message;

    public AccountIdSigninFailedException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
