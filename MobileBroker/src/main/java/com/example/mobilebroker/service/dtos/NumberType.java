package com.example.mobilebroker.service.dtos;

public enum NumberType {
    GEOGRAPHIC,
    NON_GEOGRAPHIC,
    MILITARY;

    public static NumberType fromString(String value) {

        return NON_GEOGRAPHIC;
    }
}
