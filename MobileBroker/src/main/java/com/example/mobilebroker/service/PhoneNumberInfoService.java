package com.example.mobilebroker.service;

import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import io.vavr.control.Either;

public interface PhoneNumberInfoService {

    Either<PhoneNumberInfoLookupError, PhoneNumberInfo> findOperator(String phoneNumber);
}
