package com.example.mobilebroker.service;

import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;

public interface APIKeyService {

    APIKeyResponse createApiKey(String tenantName);

    APIKeyResponse getAPIKeyByTenantName(String tenantName);

    APIKeyResponse updateApiKeyAndTenantName(String name, String newName);

    APIKeyResponse updateAPIKeyStatus(String tenantName, boolean active);

    void deleteApiKeyAndTenant(String tenantName);

}
