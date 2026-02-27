package com.example.mobilebroker.service;

import com.example.mobilebroker.dto.OperatorResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface OperatorService {

    Optional<OperatorResponse> findOperator(String phoneNumber);
}
