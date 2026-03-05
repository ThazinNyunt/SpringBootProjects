package com.example.mobilebroker.service.dtos;

public record SmsResult(
        String messageId,
        String message,
        String to,
        String network,
        String from,
        String status,
        String createdAt) {
}
