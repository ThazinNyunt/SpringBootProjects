package com.example.mobilebroker.service;


import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.integrations.infobip.InfoBipClient;
import com.example.mobilebroker.integrations.smspoh.SmsPohClient;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohRequest;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohResponse;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsServiceImpl implements SmsService {

    private final PhoneNumberInfoService phoneNumberInfoService;
    private final SmsPohClient smsPohClient;
    private final InfoBipClient infoBipClient;

    public SmsServiceImpl(PhoneNumberInfoService phoneNumberInfoService, SmsPohClient smsPohClient, InfoBipClient infoBipClient) {
        this.phoneNumberInfoService = phoneNumberInfoService;
        this.smsPohClient = smsPohClient;
        this.infoBipClient = infoBipClient;
    }

    @Override
    public Either<PhoneNumberInfoLookupError, SmsResult> sendSms(SmsRequest request) {

        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> result = phoneNumberInfoService.findOperator(request.phoneNumber());

        if(result.isLeft()) {
            return Either.left(result.getLeft());
        }

        PhoneNumberInfo info = result.get();

        SmsPohRequest smsRequest = new SmsPohRequest(
                request.phoneNumber(),
                request.message(),
                request.from()
        );

        ResponseEntity<SmsPohResponse> response = smsPohClient.sendSms(smsRequest);

        if(isSuccess(response)) {
            return Either.right(getSmsResult(response, info.operator(), smsPohClient.getProviderName()));
        }

        ResponseEntity<SmsPohResponse> fallbackResponse = infoBipClient.sendSms(smsRequest);

        return Either.right(getSmsResult(fallbackResponse, info.operator(), infoBipClient.getProviderName()));
    }

    private boolean isSuccess(ResponseEntity<SmsPohResponse> response) {

        if(!response.getStatusCode().is2xxSuccessful()) {
            return false;
        }

        SmsPohResponse body = response.getBody();

        if(body == null || body.getMessages() == null || body.getMessages().isEmpty()) {
            return false;
        }

        SmsPohResponse.Message message = body.getMessages().get(0);

        return "Accepted".equalsIgnoreCase(message.getStatus());
    }

    private SmsResult getSmsResult(ResponseEntity<SmsPohResponse> responseEntity, String operator, String provider ) {

        if(!responseEntity.getStatusCode().is2xxSuccessful()) {
            return new SmsResult(
                    null,
                    "Failed",
                    provider,
                    responseEntity.getStatusCode().value(),
                    operator,
                    LocalDateTime.now().toString()
            );
        }

        SmsPohResponse body = responseEntity.getBody();

        if (body == null || body.getMessages() == null || body.getMessages().isEmpty()) {
            throw new RuntimeException("SMS provider returned empty response");
        }

        SmsPohResponse.Message message = body.getMessages().get(0);

        return new SmsResult(
                message.getMessageId(),
                message.getStatus(),
                provider,
                responseEntity.getStatusCode().value(),
                message.getNetwork(),
                message.getCreatedAt()
        );

    }
}
