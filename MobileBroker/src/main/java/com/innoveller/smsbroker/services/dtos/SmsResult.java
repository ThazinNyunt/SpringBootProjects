package com.innoveller.smsbroker.services.dtos;

public record SmsResult(
        String messageId,
        String status,
        String provider,
        int httpStatus,
        String operator,
        String createdAt
) {}