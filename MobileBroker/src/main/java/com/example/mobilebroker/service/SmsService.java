package com.example.mobilebroker.service;

import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;

public interface SmsService {

    Either<PhoneNumberInfoLookupError, SmsResult> sendSms(SmsRequest request);
}
