package com.innoveller.smsbroker.entities;

public enum ProviderType {

    SMSPOH,
    INFOBIP;

    public static ProviderType from(String value) {
        return ProviderType.valueOf(value.toUpperCase());
    }
}
