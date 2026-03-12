package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.exceptions.SmsError;
import com.innoveller.smsbroker.services.dtos.SmsRequest;
import com.innoveller.smsbroker.services.dtos.SmsResult;
import io.vavr.control.Either;

public interface SmsService {

    Either<SmsError, SmsResult> sendSms(Long tenantId, SmsRequest request);

}
