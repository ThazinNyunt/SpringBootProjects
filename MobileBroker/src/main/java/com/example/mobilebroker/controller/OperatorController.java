package com.example.mobilebroker.controller;

import com.example.mobilebroker.dto.OperatorResponse;
import com.example.mobilebroker.exception.InvalidPhoneNumberException;
import com.example.mobilebroker.service.OperatorService;
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
    public ResponseEntity<OperatorResponse> getPhoneNumberInfo(@PathVariable("number") String phoneNumber) {
        return operatorService.findOperator(phoneNumber).map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidPhoneNumberException("Invalid phone number"));
    }
}
