package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.entities.ProviderType;
import com.innoveller.smsbroker.entities.SmsRouting;
import com.innoveller.smsbroker.entities.SmsSender;
import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import com.innoveller.smsbroker.exceptions.SmsError;
import com.innoveller.smsbroker.exceptions.SmsSendError;
import com.innoveller.smsbroker.integrations.infobip.InfoBipClient;
import com.innoveller.smsbroker.integrations.infobip.dtos.InfoBipRequest;
import com.innoveller.smsbroker.integrations.infobip.dtos.InfoBipResponse;
import com.innoveller.smsbroker.integrations.smspoh.SmsPohClient;
import com.innoveller.smsbroker.integrations.smspoh.dtos.SmsPohRequest;
import com.innoveller.smsbroker.integrations.smspoh.dtos.SmsPohResponse;
import com.innoveller.smsbroker.repositories.SmsRoutingRepository;
import com.innoveller.smsbroker.repositories.SmsSenderRepository;
import com.innoveller.smsbroker.services.dtos.PhoneNumberInfo;
import com.innoveller.smsbroker.services.dtos.SmsRequest;
import com.innoveller.smsbroker.services.dtos.SmsResult;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SmsServiceImpl implements SmsService {

    private final PhoneNumberInfoService phoneNumberInfoService;
    private final SmsPohClient smsPohClient;
    private final InfoBipClient infoBipClient;
    private final SmsRoutingRepository smsRoutingRepository;
    private final SmsSenderRepository smsSenderRepository;

    public SmsServiceImpl(PhoneNumberInfoService phoneNumberInfoService, SmsPohClient smsPohClient, InfoBipClient infoBipClient, SmsRoutingRepository smsRoutingRepository, SmsSenderRepository smsSenderRepository) {
        this.phoneNumberInfoService = phoneNumberInfoService;
        this.smsPohClient = smsPohClient;
        this.infoBipClient = infoBipClient;
        this.smsRoutingRepository = smsRoutingRepository;
        this.smsSenderRepository = smsSenderRepository;
    }


    @Override
    public Either<SmsError, SmsResult> sendSms(Long tenantId, SmsRequest request) {

        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> result = phoneNumberInfoService.findOperator(request.phoneNumber());

        if(result.isLeft()) {
            return Either.left(result.getLeft());
        }

        PhoneNumberInfo info = result.get();
        String operator = info.operator();

        List<SmsRouting> routes = smsRoutingRepository.findByTenantAndOperator(tenantId, operator);
        if(routes.isEmpty()) {
            return Either.left(new SmsSendError.FailedNoProvider(operator));
        }

        boolean senderNameFound = false;

        for(SmsRouting route : routes) {
            ProviderType providerType = route.getProvider().getProviderType();
            String providerId = route.getProvider().getProviderId();
            Optional<SmsSender> sender = smsSenderRepository.findByTenantAndProvider(tenantId, providerId);
            if(sender.isEmpty()) {
                continue;
            }
            senderNameFound = true;
            String senderName = sender.get().getSenderName();

            switch (providerType) {
                case SMSPOH -> {
                    SmsResult smsResult = trySendWithSMSPoh(request, operator, senderName);
                    if(smsResult != null) {
                        return Either.right(smsResult);
                    }
                }
                case INFOBIP -> {
                    SmsResult smsResult = trySendWithInfobip(request, operator, senderName);
                    if(smsResult != null) {
                        return Either.right(smsResult);
                    }
                }
            }
        }

        if(!senderNameFound) {
            return Either.left(new SmsSendError.FailedSenderNameNotConfigured(tenantId));
        }


        return Either.left(new SmsSendError.FailedProviderError(operator));
    }

    private SmsResult trySendWithSMSPoh(SmsRequest request, String operator, String senderName) {

        try {
            SmsPohRequest smsRequest = new SmsPohRequest(
                    request.phoneNumber(),
                    request.message(),
                    senderName
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
                    "SUCCESS",
                    smsPohClient.getProviderName(),
                    response.getStatusCode().value(),
                    operator,
                    message.getCreatedAt()
            );
        } catch (RestClientException ex) {
            System.out.println("SMSPOH provider error: " + ex.getMessage());
            return null;
        }
    }

    private SmsResult trySendWithInfobip(SmsRequest request, String operator, String senderName) {

        try {
            InfoBipRequest smsRequest = new InfoBipRequest(
                    request.phoneNumber(),
                    request.message(),
                    senderName
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
                    "SUCCESS",
                    infoBipClient.getProviderName(),
                    response.getStatusCode().value(),
                    operator,
                    LocalDateTime.now().toString()
            );
        } catch (RestClientException ex) {
            System.out.println("INFOBIP provider error: " + ex.getMessage());
            return null;
        }

    }
}
