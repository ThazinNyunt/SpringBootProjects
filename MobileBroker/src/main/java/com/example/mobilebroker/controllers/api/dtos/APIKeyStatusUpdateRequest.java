package com.example.mobilebroker.controllers.api.dtos;

public class APIKeyStatusUpdateRequest {

    private String clientName;
    private boolean active;

    public APIKeyStatusUpdateRequest(boolean active, String clientName) {
        this.active = active;
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public boolean getActive() {
        return active;
    }
}
