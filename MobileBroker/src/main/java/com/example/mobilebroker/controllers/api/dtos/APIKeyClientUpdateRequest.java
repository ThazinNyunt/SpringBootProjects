package com.example.mobilebroker.controllers.api.dtos;

public class APIKeyClientUpdateRequest {

    private String clientName;
    private String newClientName;

    public APIKeyClientUpdateRequest(String clientName, String newClientName) {
        this.clientName = clientName;
        this.newClientName = newClientName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getNewClientName() {
        return newClientName;
    }
}
