package com.example.mobilebroker.controllers.api;

import com.example.mobilebroker.controllers.api.dtos.SmsSendRequest;
import com.example.mobilebroker.controllers.api.dtos.SmsSendResponse;
import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.exception.ProblemDetails;
import com.example.mobilebroker.service.SmsService;
import com.example.mobilebroker.service.dtos.SmsRequest;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public Either<ProblemDetails, SmsSendResponse> sendSms(@RequestBody SmsSendRequest request) {
        return smsService.sendSms(
                new SmsRequest(request.getTo(), request.getMessage(), request.getFrom() )
        ).bimap(
                this::mapToApiError,
                result -> new SmsSendResponse(
                        result.status(),
                        result.provider(),
                        result.operator(),
                        result.createdAt()
                )
        );
    }

    private ProblemDetails mapToApiError(PhoneNumberInfoLookupError error) {
        return ProblemDetails.builder()
                .type("https://example.com/problems/sms-error")
                .title("SMS Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(error.toString())
                .build();
    }
}
