package com.example.mobilebroker.service;

import com.example.mobilebroker.dto.OperatorResponse;
import org.springframework.stereotype.Service;

public interface OperatorService {

    OperatorResponse findOperator(String phoneNumber);
}
