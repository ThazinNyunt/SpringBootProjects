package com.example.mobilebroker.service;

import com.example.mobilebroker.exception.APIKeyError;
import com.example.mobilebroker.service.dtos.APIKeyInfo;
import io.vavr.control.Either;

public interface APIKeyService {

    Either<APIKeyError, APIKeyInfo> createApiKey(String tenantName);

    Either<APIKeyError, APIKeyInfo> getAPIKeyByTenantName(String tenantName);

    Either<APIKeyError, APIKeyInfo> updateApiKeyAndTenantName(String name, String newName);

    Either<APIKeyError, APIKeyInfo> updateAPIKeyStatus(String tenantName, boolean active);

    Either<APIKeyError, Void> deleteApiKeyAndTenant(String tenantName);

}
