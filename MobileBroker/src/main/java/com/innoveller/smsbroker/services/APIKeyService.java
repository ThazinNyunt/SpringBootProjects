package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.exceptions.APIKeyError;
import com.innoveller.smsbroker.services.dtos.APIKeyInfo;
import io.vavr.control.Either;

public interface APIKeyService {

    Either<APIKeyError, APIKeyInfo> createApiKey(String name);

    Either<APIKeyError, APIKeyInfo> getAPIKeyByName(String name);

    Either<APIKeyError, APIKeyInfo> updateApiKeyAndName(String name, String newName);

    Either<APIKeyError, APIKeyInfo> updateAPIKeyStatus(String name, boolean active);

    Either<APIKeyError, Void> deleteApiKeyAndTenant(String name);

}
