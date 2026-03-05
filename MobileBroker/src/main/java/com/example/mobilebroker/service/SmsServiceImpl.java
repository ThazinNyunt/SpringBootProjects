package com.example.mobilebroker.service;

import com.example.mobilebroker.client.SmsPohClient;
import com.example.mobilebroker.client.dtos.SmsPohRequest;
import com.example.mobilebroker.client.dtos.SmsPohResponse;
import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    private final PhoneNumberInfoService phoneNumberInfoService;
    private final SmsPohClient smsPohClient;

    public SmsServiceImpl(PhoneNumberInfoService phoneNumberInfoService, SmsPohClient smsPohClient) {
        this.phoneNumberInfoService = phoneNumberInfoService;
        this.smsPohClient = smsPohClient;
    }

    @Override
    public Either<PhoneNumberInfoLookupError, SmsResult> sendSms(SmsRequest request) {

        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> result = phoneNumberInfoService.findOperator(request.phoneNumber());

        if(result.isLeft()) {
            return Either.left(result.getLeft());
        }

        SmsPohRequest smsPohRequest = new SmsPohRequest(
                request.phoneNumber(),
                request.message(),
                request.from()
        );

        SmsPohResponse response = smsPohClient.sendSms(smsPohRequest);

        SmsResult smsResult = getSmsResult(response);

        return Either.right(smsResult);
    }

    private static SmsResult getSmsResult(SmsPohResponse response) {

        if (response.getMessages() == null || response.getMessages().isEmpty()) {
            throw new RuntimeException("SMS provider returned empty response");
        }

        SmsPohResponse.Message message = response.getMessages().get(0);

        return new SmsResult(
                message.getMessageId(),
                message.getMessage(),
                message.getTo(),
                message.getNetwork(),
                message.getFrom(),
                message.getStatus(),
                message.getCreatedAt()
        );
    }
}
