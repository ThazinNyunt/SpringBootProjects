package com.example.mobilebroker.exception;

public class TentantNotFoundException extends APIKeyException {

    public TentantNotFoundException(String tenantName) {
        super(
                "TENANT_NOT_FOUND",
                "Tenant not found: " + tenantName
        );
    }

}