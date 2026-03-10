package com.example.mobilebroker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderNameId implements Serializable {

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "provider_id")
    private String providerId;

}
