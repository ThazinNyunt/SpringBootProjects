package com.example.mobilebroker.service;

import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;
import com.example.mobilebroker.entity.APIKey;
import com.example.mobilebroker.entity.Tenant;
import com.example.mobilebroker.exception.TenantAlreadyExistsException;
import com.example.mobilebroker.exception.TentantNotFoundException;
import com.example.mobilebroker.repository.APIKeyRepository;
import com.example.mobilebroker.repository.TenantRepository;
import com.example.mobilebroker.util.ApiKeyGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class APIKeyServiceImpl implements APIKeyService {

    private final APIKeyRepository apiKeyRepository;
    private final TenantRepository tenantRepository;

    public APIKeyServiceImpl(APIKeyRepository apiKeyRepository, TenantRepository tenantRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public APIKeyResponse createApiKey(String tenantName) {

        Tenant tenant = tenantRepository.findByTenantName(tenantName)
                .orElseGet(() -> {
                    Tenant newTenant = new Tenant();
                    newTenant.setTenantName(tenantName);
                    return tenantRepository.save(newTenant);
                });

        if(apiKeyRepository.existsByTenant(tenant)) {
            throw new TenantAlreadyExistsException(tenantName);
        }

        String key = ApiKeyGenerator.generate();

        APIKey apiKey = new APIKey();
        apiKey.setTenant(tenant);
        apiKey.setApiKey(key);
        apiKey.setActive(true);

        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(tenant.getTenantName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse getAPIKeyByTenantName(String tenantName) {

        Tenant tenant = tenantRepository.findByTenantName(tenantName)
                .orElseThrow(()-> new TentantNotFoundException(tenantName));

        APIKey apiKey = apiKeyRepository.findByTenant(tenant)
                .orElseThrow(() -> new TentantNotFoundException(tenantName));

        return new APIKeyResponse(tenant.getTenantName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse updateApiKeyAndTenantName(String tenantName, String newName) {
        Tenant tenant = tenantRepository.findByTenantName(tenantName)
                .orElseThrow(() -> new TentantNotFoundException(tenantName));

        APIKey apiKey = apiKeyRepository.findByTenant(tenant)
                .orElseThrow(() -> new TentantNotFoundException(tenantName));

        tenant.setTenantName(newName);
        tenantRepository.save(tenant);

        String newKey = ApiKeyGenerator.generate();
        apiKey.setApiKey(newKey);
        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(tenant.getTenantName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public APIKeyResponse updateAPIKeyStatus(String tenantName, boolean active) {
        Tenant tenant = tenantRepository.findByTenantName(tenantName)
                .orElseThrow(() -> new TentantNotFoundException(tenantName));

        APIKey apiKey = apiKeyRepository.findByTenant(tenant)
                .orElseThrow(() -> new TentantNotFoundException(tenantName));

        apiKey.setActive(active);
        apiKeyRepository.save(apiKey);

        return new APIKeyResponse(tenant.getTenantName(), apiKey.getApiKey(), apiKey.getActive());
    }

    @Override
    public void deleteApiKeyAndTenant(String tenantName) {
       Tenant tenant = tenantRepository.findByTenantName(tenantName)
               .orElseThrow(() -> new TentantNotFoundException(tenantName));

       APIKey apiKey = apiKeyRepository.findByTenant(tenant)
               .orElseThrow(() -> new TentantNotFoundException(tenantName));

       apiKeyRepository.delete(apiKey);
       tenantRepository.delete(tenant);
    }
}
