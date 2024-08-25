package com.tourist.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("user not found : " + email);
    }
}
