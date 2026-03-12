package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sms_routing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
