package com.example.mobilebroker.service;

import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;
import com.example.mobilebroker.entity.APIKey;
import com.example.mobilebroker.entity.Tenant;
import com.example.mobilebroker.exception.APIKeyError;
import com.example.mobilebroker.repository.APIKeyRepository;
import com.example.mobilebroker.repository.SenderNameRepository;
import com.example.mobilebroker.repository.TenantRepository;
import com.example.mobilebroker.service.dtos.APIKeyInfo;
import com.example.mobilebroker.util.ApiKeyGenerator;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class APIKeyServiceImpl implements APIKeyService {

    private final APIKeyRepository apiKeyRepository;
    private final TenantRepository tenantRepository;
    private final SenderNameRepository senderNameRepository;

    public APIKeyServiceImpl(APIKeyRepository apiKeyRepository, TenantRepository tenantRepository, SenderNameRepository senderNameRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.tenantRepository = tenantRepository;
        this.senderNameRepository = senderNameRepository;
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> createApiKey(String tenantName) {

        Tenant tenant = tenantRepository.findByTenantName(tenantName)
                .orElseGet(() -> {
                    Tenant newTenant = new Tenant();
                    newTenant.setTenantName(tenantName);
                    return tenantRepository.save(newTenant);
                });

        if(apiKeyRepository.existsByTenant(tenant)) {
            return Either.left(new APIKeyError.TenantAlreadyExists(tenantName));
        }

        String key = ApiKeyGenerator.generate();

        APIKey apiKey = new APIKey();
        apiKey.setTenant(tenant);
        apiKey.setApiKey(key);
        apiKey.setActive(true);

        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                        tenant.getTenantName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> getAPIKeyByTenantName(String tenantName) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(tenantName);

        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();

        return Either.right(
                new APIKeyInfo(
                        apiKey.getTenant().getTenantName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> updateApiKeyAndTenantName(String tenantName, String newName) {

        if(tenantRepository.existsByTenantName(newName)) {
            return Either.left(new APIKeyError.TenantAlreadyExists(newName));
        }

        Either<APIKeyError, APIKey> result = getTenantAndApiKey(tenantName);
        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();
        Tenant tenant = apiKey.getTenant();

        tenant.setTenantName(newName);
        tenantRepository.save(tenant);

        String newKey = ApiKeyGenerator.generate();
        apiKey.setApiKey(newKey);
        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                        apiKey.getTenant().getTenantName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> updateAPIKeyStatus(String tenantName, boolean active) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(tenantName);

        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();

        apiKey.setActive(active);
        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                    apiKey.getTenant().getTenantName(),
                    apiKey.getApiKey(),
                    apiKey.getActive()
                    )
                );
    }

    @Override
    public Either<APIKeyError, Void> deleteApiKeyAndTenant(String tenantName) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(tenantName);
        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();
        Tenant tenant = apiKey.getTenant();

        senderNameRepository.deleteByTenant(tenant);
        apiKeyRepository.delete(apiKey);
        tenantRepository.delete(tenant);

       return Either.right(null);
    }

    private Either<APIKeyError, APIKey> getTenantAndApiKey(String tenantName) {
        var tenantOptional = tenantRepository.findByTenantName(tenantName);
        if (tenantOptional.isEmpty()) {
            return Either.left(new APIKeyError.TenantNotFound(tenantName));
        }
        Tenant tenant = tenantOptional.get();

        var apiKeyOptional = apiKeyRepository.findByTenant(tenant);
        if (apiKeyOptional.isEmpty()) {
            return Either.left(new APIKeyError.TenantNotFound(tenantName));
        }

        return Either.right(apiKeyOptional.get());
    }

}
