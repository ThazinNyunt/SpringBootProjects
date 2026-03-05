package com.example.mobilebroker.controllers.api.dtos;

public class SmsSendResponse {

    private String messageId;
    private String message;
    private String to;
    private String network;
    private String from;
    private String status;
    private String createdAt;

    public SmsSendResponse(String messageId, String message, String to, String network, String from, String status, String createdAt) {
        this.messageId = messageId;
        this.message = message;
        this.to = to;
        this.network = network;
        this.from = from;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }
    public String getMessage() {
        return message;
    }
    public String getTo() {
        return to;
    }
    public String getNetwork() {
        return network;
    }
    public String getFrom() {
        return from;
    }
    public String getStatus() {
        return status;
    }
    public String getCreatedAt() {
        return createdAt;
    }
}
