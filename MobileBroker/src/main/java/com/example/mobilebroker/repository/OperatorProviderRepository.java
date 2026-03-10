package com.example.mobilebroker.repository;

import com.example.mobilebroker.entity.OperatorProvider;
import com.example.mobilebroker.entity.OperatorProviderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperatorProviderRepository extends JpaRepository<OperatorProvider, OperatorProviderId> {

    @Query(value = """
            select *
            from operator_provider
            where operator_id= :operatorId
            order by priority asc
            """, nativeQuery = true)
    List<OperatorProvider> findByOperatorIdOrderByPriority(@Param("operatorId") String operatorId);

}
