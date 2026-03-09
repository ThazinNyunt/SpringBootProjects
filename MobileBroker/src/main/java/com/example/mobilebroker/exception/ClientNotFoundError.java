package com.example.mobilebroker.exception;

public class ClientNotFoundError extends APIKeyError {

    public ClientNotFoundError(String clientName) {
        super("Client not found: " + clientName);
    }

}