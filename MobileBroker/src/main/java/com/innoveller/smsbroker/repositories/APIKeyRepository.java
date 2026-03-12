package com.innoveller.smsbroker.repositories;

import com.innoveller.smsbroker.entities.APIKey;
import com.innoveller.smsbroker.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface APIKeyRepository extends JpaRepository<APIKey, Long> {

    boolean existsByApiKeyAndActiveTrue(String apiKey);

    boolean existsByTenant(Tenant tenant);

    Optional<APIKey> findByTenant(Tenant tenant);

    Optional<APIKey> findByApiKeyAndActiveTrue(String apiKey);
}
