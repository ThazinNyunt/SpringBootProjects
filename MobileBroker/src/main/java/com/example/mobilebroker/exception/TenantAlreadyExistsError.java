package com.example.mobilebroker.exception;

public class TenantAlreadyExistsError extends APIKeyError {

    public TenantAlreadyExistsError(String clientName) {
        super("Client already exists: " + clientName);
    }
}