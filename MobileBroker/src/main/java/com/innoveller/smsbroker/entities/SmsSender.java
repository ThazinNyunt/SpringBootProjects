package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sms_sender")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
