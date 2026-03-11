package com.example.mobilebroker.exception;

public class APIKeyException extends RuntimeException {

    private final String detail;

    public APIKeyException(String message, String detail) {
        super(message);
        this.detail = detail;
    }
    public String getDetail() {
        return detail;
    }
}
