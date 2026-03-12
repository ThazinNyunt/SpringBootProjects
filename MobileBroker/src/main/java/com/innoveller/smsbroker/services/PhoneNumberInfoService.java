package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import com.innoveller.smsbroker.services.dtos.PhoneNumberInfo;
import io.vavr.control.Either;

public interface PhoneNumberInfoService {

    Either<PhoneNumberInfoLookupError, PhoneNumberInfo> findOperator(String phoneNumber);
}
