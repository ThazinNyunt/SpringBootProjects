package com.innoveller.smsbroker.services.dtos;

public record APIKeyInfo(
        String tenantName,
        String apiKey,
        boolean active
) {}