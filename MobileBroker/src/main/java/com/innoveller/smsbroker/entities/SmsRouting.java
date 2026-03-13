package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sms_routing")
public class SmsRouting {

    @EmbeddedId
    private SmsRoutingId smsRoutingId;

    @ManyToOne
    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @MapsId("operatorId")
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "priority")
    private Integer priority;

    public SmsRouting() {
    }

    public SmsRouting(SmsRoutingId smsRoutingId, Tenant tenant, Operator operator, Provider provider, Integer priority) {
        this.smsRoutingId = smsRoutingId;
        this.tenant = tenant;
        this.operator = operator;
        this.provider = provider;
        this.priority = priority;
    }

    public SmsRoutingId getSmsRoutingId() {
        return smsRoutingId;
    }

    public void setSmsRoutingId(SmsRoutingId smsRoutingId) {
        this.smsRoutingId = smsRoutingId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
