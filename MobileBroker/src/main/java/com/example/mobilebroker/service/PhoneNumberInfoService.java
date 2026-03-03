package com.example.mobilebroker.service;

import com.example.mobilebroker.controller.api.dtos.PhoneNumberInfoResponse;
import io.vavr.control.Either;

public interface PhoneNumberInfoService {

    Either<Object, PhoneNumberInfoResponse> findOperator(String phoneNumber);
}
