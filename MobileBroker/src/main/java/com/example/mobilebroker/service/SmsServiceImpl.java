package com.example.mobilebroker.service;

import com.example.mobilebroker.entity.OperatorProvider;
import com.example.mobilebroker.entity.SenderName;
import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.integrations.infobip.InfoBipClient;
import com.example.mobilebroker.integrations.infobip.dtos.InfoBipRequest;
import com.example.mobilebroker.integrations.infobip.dtos.InfoBipResponse;
import com.example.mobilebroker.integrations.smspoh.SmsPohClient;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohRequest;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohResponse;
import com.example.mobilebroker.repository.OperatorProviderRepository;
import com.example.mobilebroker.repository.SenderNameRepository;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.service.dtos.SmsRequest;
import com.example.mobilebroker.service.dtos.SmsResult;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletRequest;
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
    private final OperatorProviderRepository operatorProviderRepository;
    private final HttpServletRequest httpServletRequest;
    private final SenderNameRepository senderNameRepository;

    public SmsServiceImpl(PhoneNumberInfoService phoneNumberInfoService, SmsPohClient smsPohClient, InfoBipClient infoBipClient, OperatorProviderRepository operatorProviderRepository, HttpServletRequest httpServletRequest, SenderNameRepository senderNameRepository) {
        this.phoneNumberInfoService = phoneNumberInfoService;
        this.smsPohClient = smsPohClient;
        this.infoBipClient = infoBipClient;
        this.operatorProviderRepository = operatorProviderRepository;
        this.httpServletRequest = httpServletRequest;
        this.senderNameRepository = senderNameRepository;
    }

    @Override
    public Either<PhoneNumberInfoLookupError, SmsResult> sendSms(SmsRequest request) {

        Long tenantId = (Long) httpServletRequest.getAttribute("tenantId");

        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> result = phoneNumberInfoService.findOperator(request.phoneNumber());

        if(result.isLeft()) {
            return Either.left(result.getLeft());
        }

        PhoneNumberInfo info = result.get();
        String operator = info.operator();

        List<OperatorProvider> providers = operatorProviderRepository.findByOperatorIdOrderByPriority(operator);
        if(providers.isEmpty()) {
            return Either.right(
                    new SmsResult(
                            null,
                            "FAILED_NO_PROVIDER",
                            null,
                            404,
                            operator,
                            LocalDateTime.now().toString()
                    )
            );
        }

        boolean senderNameFound = false;

        for(OperatorProvider op : providers) {
            String providerId = op.getProvider().getProviderId();
            Optional<SenderName> senderNameOptional = senderNameRepository.findByTenantAndProvider(tenantId, providerId);
            if(senderNameOptional.isEmpty()) {
                continue;
            }
            senderNameFound = true;
            String senderName = senderNameOptional.get().getSenderName();

            if("SMSPOH".equals(providerId)) {
                SmsResult smsResult = sendWithSMSPoh(request, operator, senderName);
                if(smsResult != null) {
                    return Either.right(smsResult);
                }
            }
            if("INFOBIP".equals(providerId)) {
                SmsResult smsResult = sendWithInfobip(request, operator, senderName);
                if(smsResult != null) {
                    return Either.right(smsResult);
                }
            }
        }

        if(!senderNameFound) {
            return Either.right(
                    new SmsResult(
                            null,
                            "FAILED_SENDERNAME_NOT_CONFIGURED",
                            null,
                            400,
                            operator,
                            LocalDateTime.now().toString()
                    )
            );
        }


        return Either.right(
                new SmsResult(
                        null,
                        "FAILED_PROVIDER_ERROR",
                        null,
                        500,
                        operator,
                        LocalDateTime.now().toString()
                )
        );
    }

    private SmsResult sendWithSMSPoh(SmsRequest request, String operator, String senderName) {

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

    private SmsResult sendWithInfobip(SmsRequest request, String operator, String senderName) {

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
