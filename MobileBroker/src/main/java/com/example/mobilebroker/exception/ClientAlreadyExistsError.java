package com.example.mobilebroker.exception;

public class ClientAlreadyExistsError extends APIKeyError {

    public ClientAlreadyExistsError(String clientName) {
        super("Client already exists: " + clientName);
    }
}