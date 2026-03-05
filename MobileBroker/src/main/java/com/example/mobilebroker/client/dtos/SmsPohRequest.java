package com.example.mobilebroker.client.dtos;

public class SmsPohRequest {

    private String to;
    private String message;
    private String from;

    public SmsPohRequest() {}

    public SmsPohRequest(String to, String message, String from) {
        this.to = to;
        this.message = message;
        this.from = from;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
}
