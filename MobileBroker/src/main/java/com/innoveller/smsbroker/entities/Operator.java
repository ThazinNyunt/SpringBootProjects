package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "operator")
public class    Operator {

    @Id
    @Column(name = "operator_id", nullable = false)
    private String operatorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    public Operator() {
    }

    public Operator(String operatorId, String name, Integer countryCode) {
        this.operatorId = operatorId;
        this.name = name;
        this.countryCode = countryCode;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }
}
