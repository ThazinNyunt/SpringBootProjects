package com.innoveller.smsbroker.services.dtos;

public record SmsRequest(
        String phoneNumber,
        String message) {

}
