package com.example.mobilebroker.exception;

public class APIKeyError extends RuntimeException {
    public APIKeyError(String message) {
        super(message);
    }
}
