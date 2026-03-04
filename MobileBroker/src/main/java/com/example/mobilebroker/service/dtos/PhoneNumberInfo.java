package com.example.mobilebroker.service.dtos;

public class PhoneNumberInfo {
    private final String operator;
    private final NdcInfo ndcInfo;

    public PhoneNumberInfo(String operator, NdcInfo ndcInfo) {
        this.operator = operator;
        this.ndcInfo = ndcInfo;
    }

    public String operator() {
        return operator;
    }

    public NdcInfo ndcInfo() {
        return ndcInfo;
    }
}
