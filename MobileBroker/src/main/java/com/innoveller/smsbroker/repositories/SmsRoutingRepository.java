package com.innoveller.smsbroker.repositories;

import com.innoveller.smsbroker.entities.SmsRouting;
import com.innoveller.smsbroker.entities.SmsRoutingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmsRoutingRepository extends JpaRepository<SmsRouting, SmsRoutingId> {

    @Query(value = """
            select *
            from sms_routing
            where tenant_id = :tenantId
            and operator_id = :operatorId
            order by priority asc
            """, nativeQuery = true)
    List<SmsRouting> findByTenantAndOperator(@Param("tenantId") Long tenantId, @Param("operatorId") String operatorId);
}
