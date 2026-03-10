package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByTenantName(String tenantName);
}
