package com.innoveller.smsbroker.services.dtos;

public record APIKeyInfo(
        String name,
        String apiKey,
        boolean active
) {}