package com.example.mobilebroker.exception;

public class TentantNotFoundError extends APIKeyError {

    public TentantNotFoundError(String clientName) {
        super("Client not found: " + clientName);
    }

}