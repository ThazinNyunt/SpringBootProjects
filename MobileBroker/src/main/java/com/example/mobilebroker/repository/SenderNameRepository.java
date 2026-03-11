package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.SenderName;
import com.example.mobilebroker.entity.SenderNameId;
import com.example.mobilebroker.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SenderNameRepository extends JpaRepository<SenderName, SenderNameId> {

    @Query(value = """
            select *
            from tenant_provider_sender_name
            where tenant_id = :tenantId
            and provider_id = :providerId
            """, nativeQuery = true)
    Optional<SenderName> findByTenantAndProvider(@Param("tenantId") Long tenantId, @Param("providerId") String providerId);

    void deleteByTenant(Tenant tenant);

}
