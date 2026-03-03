package com.example.mobilebroker.controller.api;

import com.example.mobilebroker.controller.api.dtos.ApiErrorResponse;
import com.example.mobilebroker.controller.api.dtos.PhoneNumberInfoResponse;
import com.example.mobilebroker.error.PhoneNumberInfoLookupError;
import com.example.mobilebroker.exception.ProblemDetails;
import com.example.mobilebroker.service.PhoneNumberInfoService;
import com.example.mobilebroker.service.dtos.GeographicInfo;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone-numbers")
public class OperatorController {

    private final PhoneNumberInfoService operatorService;

    public OperatorController(PhoneNumberInfoService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/{number}")
    public Either<ProblemDetails, PhoneNumberInfoResponse> getPhoneNumberInfo(@PathVariable("number") String phoneNumber) {
        Either<PhoneNumberInfoLookupError, PhoneNumberInfo> errorOrInfo = operatorService.findOperator(phoneNumber);
        Either<ProblemDetails, PhoneNumberInfoResponse> some = operatorService.findOperator(phoneNumber).bimap(
                error -> mapToApiError((PhoneNumberInfoLookupError) error),
                info -> {
                    String area = info.geographicInfo().map(GeographicInfo::name).orElse("");
                    String numberType = ""; //TODO
                    return new PhoneNumberInfoResponse(phoneNumber, info.operator(), area, numberType);
                });
        return some;
    }

    private ProblemDetails mapToApiError(PhoneNumberInfoLookupError error) {
        if(error instanceof PhoneNumberInfoLookupError.InvalidPhoneNumber e) {
            return ProblemDetails.builder()
                    .type("https//example.com/problem/invalid-phone-number")
                    .title("Invalid Phone Number")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail("No operator found for: " + e.phoneNumber())
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

        //if (error instanceof PhoneNumberInfoLookupError.OperatorNotFound e) {
            return ProblemDetails.builder()
                    .type("https://example.com/problems/operator-not-found")
                    .title("Operator Not Found")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail("No operator found for: " + e.phoneNumber())
                    .build();
        //}
    }
}
