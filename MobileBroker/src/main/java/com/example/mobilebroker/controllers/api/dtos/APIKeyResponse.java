package com.example.mobilebroker.controllers.api.dtos;

public class APIKeyResponse {

    private String clientName;
    private String apiKey;
    private boolean active;

    public APIKeyResponse(String clientName, String apiKey, boolean active) {
        this.clientName = clientName;
        this.apiKey = apiKey;
        this.active = active;
    }

    public String getClientName() {
        return clientName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean getActive() {
        return active;
    }
}
