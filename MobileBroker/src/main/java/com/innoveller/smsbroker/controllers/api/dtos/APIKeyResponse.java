package com.innoveller.smsbroker.controllers.api.dtos;

public class APIKeyResponse {

    private String tenantName;
    private String apiKey;
    private boolean active;

    public APIKeyResponse(String tenantName, String apiKey, boolean active) {
        this.tenantName = tenantName;
        this.apiKey = apiKey;
        this.active = active;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean getActive() {
        return active;
    }
}
