package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.entities.APIKey;
import com.innoveller.smsbroker.entities.Tenant;
import com.innoveller.smsbroker.exceptions.APIKeyError;
import com.innoveller.smsbroker.repositories.APIKeyRepository;
import com.innoveller.smsbroker.repositories.SmsSenderRepository;
import com.innoveller.smsbroker.repositories.TenantRepository;
import com.innoveller.smsbroker.services.dtos.APIKeyInfo;
import com.innoveller.smsbroker.utils.ApiKeyGenerator;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class APIKeyServiceImpl implements APIKeyService {

    private final APIKeyRepository apiKeyRepository;
    private final TenantRepository tenantRepository;
    private final SmsSenderRepository smsSenderRepository;

    public APIKeyServiceImpl(APIKeyRepository apiKeyRepository, TenantRepository tenantRepository, SmsSenderRepository smsSenderRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.tenantRepository = tenantRepository;
        this.smsSenderRepository = smsSenderRepository;
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> createApiKey(String name) {

        Tenant tenant = tenantRepository.findByName(name)
                .orElseGet(() -> {
                    Tenant newTenant = new Tenant();
                    newTenant.setName(name);
                    return tenantRepository.save(newTenant);
                });

        if(apiKeyRepository.existsByTenant(tenant)) {
            return Either.left(new APIKeyError.TenantAlreadyExists(name));
        }

        String key = ApiKeyGenerator.generate();

        APIKey apiKey = new APIKey();
        apiKey.setTenant(tenant);
        apiKey.setApiKey(key);
        apiKey.setActive(true);

        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                        tenant.getName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> getAPIKeyByName(String name) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(name);

        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();

        return Either.right(
                new APIKeyInfo(
                        apiKey.getTenant().getName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> updateApiKeyAndName(String name, String newName) {

        if(tenantRepository.existsByName(newName)) {
            return Either.left(new APIKeyError.TenantAlreadyExists(newName));
        }

        Either<APIKeyError, APIKey> result = getTenantAndApiKey(name);
        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();
        Tenant tenant = apiKey.getTenant();

        tenant.setName(newName);
        tenantRepository.save(tenant);

        String newKey = ApiKeyGenerator.generate();
        apiKey.setApiKey(newKey);
        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                        apiKey.getTenant().getName(),
                        apiKey.getApiKey(),
                        apiKey.getActive()
                )
        );
    }

    @Override
    public Either<APIKeyError, APIKeyInfo> updateAPIKeyStatus(String name, boolean active) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(name);

        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();

        apiKey.setActive(active);
        apiKeyRepository.save(apiKey);

        return Either.right(
                new APIKeyInfo(
                    apiKey.getTenant().getName(),
                    apiKey.getApiKey(),
                    apiKey.getActive()
                    )
                );
    }

    @Override
    public Either<APIKeyError, Void> deleteApiKeyAndTenant(String name) {
        Either<APIKeyError, APIKey> result = getTenantAndApiKey(name);
        if (result.isLeft()) {
            return Either.left(result.getLeft());
        }

        APIKey apiKey = result.get();
        Tenant tenant = apiKey.getTenant();

        smsSenderRepository.deleteByTenant(tenant);
        apiKeyRepository.delete(apiKey);
        tenantRepository.delete(tenant);

       return Either.right(null);
    }

    private Either<APIKeyError, APIKey> getTenantAndApiKey(String name) {
        var tenantOptional = tenantRepository.findByName(name);
        if (tenantOptional.isEmpty()) {
            return Either.left(new APIKeyError.TenantNotFound(name));
        }
        Tenant tenant = tenantOptional.get();

        var apiKeyOptional = apiKeyRepository.findByTenant(tenant);
        if (apiKeyOptional.isEmpty()) {
            return Either.left(new APIKeyError.TenantNotFound(name));
        }

        return Either.right(apiKeyOptional.get());
    }

}
