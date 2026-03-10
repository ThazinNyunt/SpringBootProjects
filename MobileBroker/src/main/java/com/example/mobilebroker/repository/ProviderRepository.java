package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, String> {
}
