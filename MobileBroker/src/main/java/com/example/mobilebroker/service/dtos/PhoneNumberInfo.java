package com.example.mobilebroker.service.dtos;

public class PhoneNumberInfo {

    private final String operatorCode;
    private final NdcInfo ndcInfo;

    public PhoneNumberInfo(String operatorCode, NdcInfo ndcInfo) {
        this.operatorCode = operatorCode;
        this.ndcInfo = ndcInfo;
    }

    public String operatorCode() {
        return operatorCode;
    }

    public NdcInfo ndcInfo() {
        return ndcInfo;
    }
}
