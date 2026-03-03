package com.example.mobilebroker.controller;

import com.example.mobilebroker.dto.OperatorResponse;
import com.example.mobilebroker.error.OperatorError;
import com.example.mobilebroker.exception.InvalidPhoneNumberException;
import com.example.mobilebroker.service.OperatorService;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/phone-numbers")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/{number}")
    public Either<OperatorError, OperatorResponse> getPhoneNumberInfo(@PathVariable("number") String phoneNumber) {
       return operatorService.findOperator(phoneNumber);
    }
}
