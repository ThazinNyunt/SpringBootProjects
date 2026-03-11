package com.example.mobilebroker.exception;

public class SenderNameNotFoundException extends APIKeyException {

    public SenderNameNotFoundException(Long tenantId, String providerId) {
        super(
                "SENDER_NAME_NOT_FOUND",
                "SenderName not found for tenantId: " + tenantId + "and provider: " + providerId);
    }
}