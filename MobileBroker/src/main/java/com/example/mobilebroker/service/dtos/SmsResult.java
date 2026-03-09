package com.example.mobilebroker.service.dtos;

public record SmsResult(
        String messageId,
        String status,
        String provider,
        int httpStatus,
        String operator,
        String createdAt
) {}