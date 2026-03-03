package com.example.mobilebroker.service;

import com.example.mobilebroker.controller.api.dtos.PhoneNumberInfoResponse;
import com.example.mobilebroker.error.PhoneNumberInfoLookupError;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import io.vavr.control.Either;

public interface PhoneNumberInfoService {

    Either<PhoneNumberInfoLookupError, PhoneNumberInfo> findOperator(String phoneNumber);
}
