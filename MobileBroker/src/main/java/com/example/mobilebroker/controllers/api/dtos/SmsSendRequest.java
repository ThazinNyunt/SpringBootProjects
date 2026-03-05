package com.example.mobilebroker.controllers.api.dtos;

public class SmsSendRequest {

    private String to;
    private String message;
    private String from;

    public SmsSendRequest() {
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
