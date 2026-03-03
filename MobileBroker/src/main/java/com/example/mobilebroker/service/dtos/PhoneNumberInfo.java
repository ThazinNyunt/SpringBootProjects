package com.example.mobilebroker.service.dtos;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PhoneNumberInfo {
    private final String operator;
    private final GeographicInfo geographicInfo;

    public PhoneNumberInfo(String operator, @Nullable GeographicInfo geographicInfo) {
        this.operator = operator;
        this.geographicInfo = geographicInfo;
    }

    public String operator() {
        return operator;
    }

    public Optional<GeographicInfo> geographicInfo() {
        return Optional.ofNullable(geographicInfo);
    }
}
