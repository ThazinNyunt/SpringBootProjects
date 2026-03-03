package com.example.mobilebroker.service;

import com.example.mobilebroker.dto.OperatorResponse;
import com.example.mobilebroker.error.OperatorError;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface OperatorService {

    Either<OperatorError, OperatorResponse> findOperator(String phoneNumber);
}
