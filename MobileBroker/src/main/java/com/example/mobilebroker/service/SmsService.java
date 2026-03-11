package com.example.mobilebroker.service;

import com.example.mobilebroker.exception.SmsError;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;

public interface SmsService {

    Either<SmsError, SmsResult> sendSms(SmsRequest request);

}
