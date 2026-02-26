package com.example.mobilebroker.controller;

import com.example.mobilebroker.dto.OperatorResponse;
import com.example.mobilebroker.service.OperatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/operator")
    public OperatorResponse getOperator(@RequestParam String phonenumber) {
        return operatorService.findOperator(phonenumber);
    }
}
