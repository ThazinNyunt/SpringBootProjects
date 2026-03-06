package com.example.mobilebroker.controllers.api.dtos;

public class PhoneNumberInfoResponse {

    private String phoneNumber;
    private String operator;
    private String serviceArea;

    public PhoneNumberInfoResponse(String phoneNumber, String operator, String serviceArea) {
        this.phoneNumber = phoneNumber;
        this.operator = operator;
        this.serviceArea = serviceArea;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getOperator() {
        return operator;
    }
    public String getServiceArea() {
        return serviceArea;
    }
}
