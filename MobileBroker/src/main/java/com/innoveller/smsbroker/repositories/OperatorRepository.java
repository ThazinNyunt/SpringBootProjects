package com.innoveller.smsbroker.repositories;

import com.innoveller.smsbroker.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, String> {
}
