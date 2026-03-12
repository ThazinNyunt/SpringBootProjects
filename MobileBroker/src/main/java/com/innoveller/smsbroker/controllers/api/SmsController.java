package com.innoveller.smsbroker.controllers.api;

import com.innoveller.smsbroker.controllers.api.dtos.SmsSendRequest;
import com.innoveller.smsbroker.controllers.api.dtos.SmsSendResponse;
import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import com.innoveller.smsbroker.exceptions.ProblemDetails;
import com.innoveller.smsbroker.exceptions.SmsError;
import com.innoveller.smsbroker.exceptions.SmsSendError;
import com.innoveller.smsbroker.services.SmsService;
import com.innoveller.smsbroker.services.dtos.SmsRequest;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletRequest;
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
    public Either<ProblemDetails, SmsSendResponse> sendSms(@RequestBody SmsSendRequest request, HttpServletRequest httpServletRequest) {

        Long tenantId = (Long) httpServletRequest.getAttribute("tenantId");

        return smsService.sendSms( tenantId,
                new SmsRequest(request.getTo(), request.getMessage())
        ).bimap(
                this::mapToApiError,
                result -> new SmsSendResponse(
                        result.status(),
                        result.createdAt()
                )
        );
    }

    private ProblemDetails mapToApiError(SmsError error) {

        if (error instanceof PhoneNumberInfoLookupError phoneError) {

            return ProblemDetails.builder()
                    .type("https://example.com/problems/phone-error")
                    .title("PHONE_NUMBER_ERROR")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail(phoneError.toString())
                    .build();
        }

        if (error instanceof SmsSendError smsError) {

            return ProblemDetails.builder()
                    .type("https://example.com/problems/sms-error")
                    .title("SMS_SEND_FAILED")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail(smsError.toString())
                    .build();
        }

        return ProblemDetails.builder()
                .type("https://example.com/problems/unknown")
                .title("UNKNOWN_ERROR")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
