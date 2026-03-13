package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Embeddable
public class SmsRoutingId {

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "provider_id")
    private String providerId;

    public SmsRoutingId() {
    }

    public SmsRoutingId(Long tenantId, String operatorId, String providerId) {
        this.tenantId = tenantId;
        this.operatorId = operatorId;
        this.providerId = providerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
