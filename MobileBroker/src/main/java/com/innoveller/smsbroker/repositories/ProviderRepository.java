package com.innoveller.smsbroker.repositories;

import com.innoveller.smsbroker.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, String> {
}
