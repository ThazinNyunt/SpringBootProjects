package com.innoveller.smsbroker.controllers.api.dtos;

public class SmsSendRequest {

    private String to;
    private String message;

    public SmsSendRequest() {
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
