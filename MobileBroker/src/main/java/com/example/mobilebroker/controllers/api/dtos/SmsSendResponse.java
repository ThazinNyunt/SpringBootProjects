package com.example.mobilebroker.controllers.api.dtos;

public class SmsSendResponse {

    private String status;
    private String createdAt;

    public SmsSendResponse(String status, String createdAt) {
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
