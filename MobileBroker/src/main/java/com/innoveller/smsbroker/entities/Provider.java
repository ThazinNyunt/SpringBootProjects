package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "name", nullable = false)
    private String name;

    public ProviderType getProviderType() {
        return ProviderType.from(providerId);
    }

    public Provider() {
    }

    public Provider(String providerId, String name) {
        this.providerId = providerId;
        this.name = name;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
