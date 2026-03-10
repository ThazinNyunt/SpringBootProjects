package com.example.mobilebroker.service;


import com.example.mobilebroker.entity.OperatorProvider;
import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.integrations.infobip.InfoBipClient;
import com.example.mobilebroker.integrations.infobip.dtos.InfoBipRequest;
import com.example.mobilebroker.integrations.infobip.dtos.InfoBipResponse;
import com.example.mobilebroker.integrations.smspoh.SmsPohClient;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohRequest;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohResponse;
import com.example.mobilebroker.repository.OperatorProviderRepository;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {

    private final PhoneNumberInfoService phoneNumberInfoService;
    private final SmsPohClient smsPohClient;
    private final InfoBipClient infoBipClient;
    private final OperatorProviderRepository operatorProviderRepository;

    public SmsServiceImpl(PhoneNumberInfoService phoneNumberInfoService, SmsPohClient smsPohClient, InfoBipClient infoBipClient, OperatorProviderRepository operatorProviderRepository) {
        this.phoneNumberInfoService = phoneNumberInfoService;
        this.smsPohClient = smsPohClient;
        this.infoBipClient = infoBipClient;
        this.operatorProviderRepository = operatorProviderRepository;
    }

    @Override
    public Either<PhoneNumberInfoLookupError, SmsResult> sendSms(SmsRequest request) {

        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> result = phoneNumberInfoService.findOperator(request.phoneNumber());

        if(result.isLeft()) {
            return Either.left(result.getLeft());
        }

        PhoneNumberInfo info = result.get();
        String operator = info.operator();

        List<OperatorProvider> providers = operatorProviderRepository.findByOperatorIdOrderByPriority(operator);
        for(OperatorProvider op : providers) {
            String providerId = op.getProvider().getProviderId();
            if("SMSPOH".equals(providerId)) {
                SmsResult smsResult = sendWithSMSPoh(request, operator);
                if(smsResult != null) {
                    return Either.right(smsResult);
                }
            }
            if("INFOBIP".equals(providerId)) {
                SmsResult smsResult = sendWithInfobip(request, operator);
                if(smsResult != null) {
                    return Either.right(smsResult);
                }
            }
        }
        return Either.right(
                new SmsResult(
                        null,
                        "Failed",
                        "No Provider Available",
                        500,
                        operator,
                        LocalDateTime.now().toString()
                )
        );
    }

    private SmsResult sendWithSMSPoh(SmsRequest request, String operator) {

        SmsPohRequest smsRequest = new SmsPohRequest(
                request.phoneNumber(),
                request.message(),
                request.from()
        );

        ResponseEntity<SmsPohResponse> response = smsPohClient.sendSms(smsRequest);
        if(!response.getStatusCode().is2xxSuccessful()){
            return null;
        }

        SmsPohResponse body = response.getBody();
        if(body == null || body.getMessages().isEmpty()) {
            return null;
        }

        SmsPohResponse.Message message = body.getMessages().get(0);
        if(!"Accepted".equalsIgnoreCase(message.getStatus())) {
            return null;
        }
        return new SmsResult(
                message.getMessageId(),
                message.getStatus(),
                smsPohClient.getProviderName(),
                response.getStatusCode().value(),
                operator,
                message.getCreatedAt()
        );
    }

    private SmsResult sendWithInfobip(SmsRequest request, String operator) {

        InfoBipRequest smsRequest = new InfoBipRequest(
                request.phoneNumber(),
                request.message(),
                request.from()
        );

        ResponseEntity<InfoBipResponse> response = infoBipClient.sendSms(smsRequest);
        if(!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        InfoBipResponse body = response.getBody();
        if(body == null || body.getMessages().isEmpty()) {
            return null;
        }

        InfoBipResponse.Message message = body.getMessages().get(0);
        if(!"PENDING_ACCEPTED".equalsIgnoreCase(message.getStatus().getName())) {
            return null;
        }

        return new SmsResult(
                message.getMessageId(),
                message.getStatus().getName(),
                infoBipClient.getProviderName(),
                response.getStatusCode().value(),
                operator,
                LocalDateTime.now().toString()
        );

    }
}
