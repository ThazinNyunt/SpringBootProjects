package com.innoveller.smsbroker.repositories;

import com.innoveller.smsbroker.entities.SmsSender;
import com.innoveller.smsbroker.entities.SmsSenderId;
import com.innoveller.smsbroker.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsSenderRepository extends JpaRepository<SmsSender, SmsSenderId> {

    @Query(value = """
            select *
            from sms_sender
            where tenant_id = :tenantId
            and provider_id = :providerId
            """, nativeQuery = true)
    Optional<SmsSender> findByTenantAndProvider(@Param("tenantId") Long tenantId, @Param("providerId") String providerId);

    void deleteByTenant(Tenant tenant);
}
