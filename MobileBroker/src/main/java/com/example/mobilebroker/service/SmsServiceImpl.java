package com.example.mobilebroker.service;


import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.integrations.smspoh.SmsPohClient;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohRequest;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohResponse;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
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

         ResponseEntity<SmsPohResponse> responseEntity = smsPohClient.sendSms(smsPohRequest);

        SmsResult smsResult = getSmsResult(responseEntity);

        return Either.right(smsResult);
    }

    private SmsResult getSmsResult(ResponseEntity<SmsPohResponse> responseEntity) {

        SmsPohResponse body = responseEntity.getBody();

        if (body == null || body.getMessages() == null || body.getMessages().isEmpty()) {
            throw new RuntimeException("SMS provider returned empty response");
        }

        SmsPohResponse.Message message = body.getMessages().get(0);

        return new SmsResult(
                message.getStatus(),
                smsPohClient.getProviderName(),
                responseEntity.getStatusCode().value(),
                message.getNetwork(),
                message.getCreatedAt()
        );

    }
}
