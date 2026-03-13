package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sms_sender")
public class SmsSender {

    @EmbeddedId
    private SmsSenderId smsSenderId;

    @ManyToOne
    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "sender_name")
    private String senderName;

    public SmsSender() {
    }

    public SmsSender(SmsSenderId smsSenderId, Tenant tenant, Provider provider, String senderName) {
        this.smsSenderId = smsSenderId;
        this.tenant = tenant;
        this.provider = provider;
        this.senderName = senderName;
    }

    public SmsSenderId getSmsSenderId() {
        return smsSenderId;
    }

    public void setSmsSenderId(SmsSenderId smsSenderId) {
        this.smsSenderId = smsSenderId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
