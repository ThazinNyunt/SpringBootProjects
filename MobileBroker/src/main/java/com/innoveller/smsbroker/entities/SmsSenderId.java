package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Embeddable
public class SmsSenderId {

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "provider_id")
    private String providerId;

    public SmsSenderId() {
    }

    public SmsSenderId(Long tenantId, String providerId) {
        this.tenantId = tenantId;
        this.providerId = providerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
