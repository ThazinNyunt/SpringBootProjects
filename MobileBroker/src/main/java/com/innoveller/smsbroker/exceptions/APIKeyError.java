package com.innoveller.smsbroker.exceptions;

public sealed interface APIKeyError permits
    APIKeyError.TenantAlreadyExists,
    APIKeyError.TenantNotFound {

    record TenantAlreadyExists(String tenantName) implements APIKeyError {}
    record TenantNotFound(String tenantName) implements APIKeyError {}
}
