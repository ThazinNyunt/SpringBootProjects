package com.innoveller.smsbroker.services.data;

public class Ndc {

    private Integer ndc;
    private String serviceArea;
    private String numberType;

    public Ndc() {
    }

    public Integer getNdc() {
        return ndc;
    }

    public void setNdc(Integer ndc) {
        this.ndc = ndc;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }
}
