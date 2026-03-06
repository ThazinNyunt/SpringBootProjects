package com.example.mobilebroker.controllers.api.dtos;

public class SmsSendResponse {

    private String status;
    private String provider;
    private int httpStatus;
    private String operator;
    private String createdAt;

    public SmsSendResponse(String status, String provider, int httpStatus, String operator, String createdAt) {
        this.status = status;
        this.provider = provider;
        this.httpStatus = httpStatus;
        this.operator = operator;
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public String getProvider() {
        return provider;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getOperator() {
        return operator;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
