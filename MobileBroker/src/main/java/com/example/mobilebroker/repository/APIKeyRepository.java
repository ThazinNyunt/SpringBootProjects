package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.APIKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface APIKeyRepository extends JpaRepository<APIKey, Long> {

    boolean existsByApiKeyAndActiveTrue(String apiKey);

    boolean existsByClientName(String clientName);

    Optional<APIKey> findByClientName (String clientName);

    void deleteByClientName(String clientName);
}
