package com.innoveller.smsbroker.integrations.infobip.dtos;

public class InfoBipRequest {

    private String to;
    private String text;
    private String from;

    public InfoBipRequest() {}

    public InfoBipRequest(String to, String text, String from) {
        this.to = to;
        this.text = text;
        this.from = from;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getText() { return text; }
    public void setText(String message) { this.text = text; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

}
