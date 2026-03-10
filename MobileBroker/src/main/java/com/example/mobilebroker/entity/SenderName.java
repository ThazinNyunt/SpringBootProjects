package com.example.mobilebroker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenant_provider_sender_name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderName {

    @EmbeddedId
    private SenderNameId senderNameId;

    @ManyToOne
    @MapsId("tenantId")
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "sender_name", nullable = false)
    private String senderName;
}
