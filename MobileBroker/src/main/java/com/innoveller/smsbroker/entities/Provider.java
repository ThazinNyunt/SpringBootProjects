package com.innoveller.smsbroker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "name", nullable = false)
    private String name;

    public ProviderType getProviderType() {
        return ProviderType.from(providerId);
    }
}
