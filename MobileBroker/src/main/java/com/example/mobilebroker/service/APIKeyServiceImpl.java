package com.example.mobilebroker.service;

import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;
import com.example.mobilebroker.entity.APIKey;
import com.example.mobilebroker.exception.ClientAlreadyExistsError;
import com.example.mobilebroker.exception.ClientNotFoundError;
import com.example.mobilebroker.repository.APIKeyRepository;
import com.example.mobilebroker.util.ApiKeyGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class APIKeyServiceImpl implements APIKeyService {

    private final APIKeyRepository apiKeyRepository;

    public APIKeyServiceImpl(APIKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public APIKeyResponse createApiKey(String clientName) {
        if(apiKeyRepository.existsByClientName(clientName)) {
            throw new ClientAlreadyExistsError(clientName);
        }

        String key = ApiKeyGenerator.generate();

        APIKey apiKey = new APIKey();
        apiKey.setClientName(clientName);
        apiKey.setApiKey(key);
        apiKey.setActive(true);

        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(apiKey.getClientName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse getByClientName(String clientName) {
        APIKey apiKey = apiKeyRepository.findByClientName(clientName)
                .orElseThrow(() -> new ClientNotFoundError(clientName));

        return new APIKeyResponse(apiKey.getClientName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse updateApiKey(String clientName, String newClientName) {
        APIKey apiKey = apiKeyRepository.findByClientName(clientName)
                .orElseThrow(() -> new ClientNotFoundError(clientName));

        String newKey = ApiKeyGenerator.generate();

        apiKey.setClientName(newClientName);
        apiKey.setApiKey(newKey);
        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(apiKey.getClientName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse updateStatus(String clientName, boolean active) {
        APIKey apiKey = apiKeyRepository.findByClientName(clientName)
                .orElseThrow(() -> new ClientNotFoundError(clientName));

        apiKey.setActive(active);
        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(apiKey.getClientName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public void deleteApiKey(String clientName) {
        if(!apiKeyRepository.existsByClientName(clientName)) {
            throw new ClientNotFoundError(clientName);
        }
        apiKeyRepository.deleteByClientName(clientName);
    }
}
