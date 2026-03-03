package com.example.mobilebroker.controller.api.dtos;

public class PhoneNumberInfoResponse {

    private String phoneNumber;
    private String operator;
    private String area;
    private String numberType;

    public PhoneNumberInfoResponse(String phoneNumber, String operator, String area, String numberType) {
        this.phoneNumber = phoneNumber;
        this.operator = operator;
        this.area = area;
        this.numberType = numberType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOperator() {
        return operator;
    }

    public String getArea() {
        return area;
    }

    public String getNumberType() {
        return numberType;
    }
}
