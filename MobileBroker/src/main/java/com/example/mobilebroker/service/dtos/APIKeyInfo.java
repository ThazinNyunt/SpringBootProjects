package com.example.mobilebroker.service.dtos;

public record APIKeyInfo(
        String tenantName,
        String apiKey,
        boolean active
) {}