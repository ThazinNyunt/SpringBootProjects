package com.example.mobilebroker.controllers.api;

import com.example.mobilebroker.controllers.dtos.PhoneNumberInfoResponse;
import com.example.mobilebroker.exception.PhoneNumberInfoLookupError;
import com.example.mobilebroker.exception.ProblemDetails;
import com.example.mobilebroker.service.PhoneNumberInfoService;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone-numbers")
public class PhoneNumberInfoController {

    private final PhoneNumberInfoService phoneNumberInfoService;

    public PhoneNumberInfoController(PhoneNumberInfoService phoneNumberInfoService) {
        this.phoneNumberInfoService = phoneNumberInfoService;
    }

    @GetMapping("/{number}")
    public Either<ProblemDetails, PhoneNumberInfoResponse> getPhoneNumberInfo(@PathVariable("number") String phoneNumber) {
        return phoneNumberInfoService.findOperator(phoneNumber)
                .bimap(
                        this::mapToApiError,
                        info -> new PhoneNumberInfoResponse(phoneNumber, info.operator(), info.ndcInfo().serviceArea())
                );
    }

    private ProblemDetails mapToApiError(PhoneNumberInfoLookupError error) {
        if(error instanceof PhoneNumberInfoLookupError.InvalidPhoneNumber e) {
            return ProblemDetails.builder()
                    .type("https//example.com/problem/invalid-phone-number")
                    .title("Invalid Phone Number")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail(e.message())
                    .build();
        }
        if (error instanceof PhoneNumberInfoLookupError.NdcNotFound e) {
            return ProblemDetails.builder()
                    .type("https://example.com/problems/ndc-not-found")
                    .title("NDC Not Found")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail("No NDC found for: " + e.phoneNumber())
                    .build();
        }
        PhoneNumberInfoLookupError.OperatorNotFound e = (PhoneNumberInfoLookupError.OperatorNotFound) error;
        return ProblemDetails.builder()
                .type("https://example.com/problems/operator-not-found")
                .title("Operator Not Found")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("No operator found for: " + e.phoneNumber())
                .build();
    }

}
