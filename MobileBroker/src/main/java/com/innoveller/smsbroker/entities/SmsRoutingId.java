package com.innoveller.smsbroker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsRoutingId {

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "provider_id")
    private String providerId;
}
