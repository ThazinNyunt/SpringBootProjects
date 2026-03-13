package com.innoveller.smsbroker.services.data;

public record PhonePrefix(
        Integer ndc,
        String serviceArea,
        String numberType) {
}
