package com.example.mobilebroker.service;

import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;

public interface APIKeyService {

    APIKeyResponse createApiKey(String clientName);

    APIKeyResponse getByClientName(String clientName);

    APIKeyResponse updateApiKey(String clientName, String newClientName);

    APIKeyResponse updateStatus(String clientName, boolean active);

    void deleteApiKey(String clientName);

}
