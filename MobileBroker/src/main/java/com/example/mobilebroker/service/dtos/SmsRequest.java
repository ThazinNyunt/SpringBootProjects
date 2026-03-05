package com.example.mobilebroker.service.dtos;

public record SmsRequest(
        String phoneNumber,
        String message,
        String from) {

}
