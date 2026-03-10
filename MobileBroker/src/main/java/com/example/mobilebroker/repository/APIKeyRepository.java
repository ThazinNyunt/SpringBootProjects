package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.APIKey;
import com.example.mobilebroker.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface APIKeyRepository extends JpaRepository<APIKey, Long> {

    boolean existsByApiKeyAndActiveTrue(String apiKey);

    boolean existsByTenant(Tenant tenantName);

    Optional<APIKey> findByTenant(Tenant tenant);

    Optional<APIKey> findByApiKeyAndActiveTrue(String apiKey);
}
