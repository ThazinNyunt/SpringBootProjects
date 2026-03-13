package com.innoveller.smsbroker.controllers.api.dtos;

public class APIKeyResponse {

    private String name;
    private String apiKey;
    private boolean active;

    public APIKeyResponse(String name, String apiKey, boolean active) {
        this.name = name;
        this.apiKey = apiKey;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean getActive() {
        return active;
    }
}
