package com.example.mobilebroker.exception;

public class TenantAlreadyExistsException extends APIKeyException {

    public TenantAlreadyExistsException(String tenantName) {
        super(
                "TENANT_ALREADY_EXISTS",
                "Tenant already exists: " + tenantName
        );
    }
}