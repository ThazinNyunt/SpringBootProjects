package com.innoveller.smsbroker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "api_key")
public class APIKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_key_id")
    private Long apiKeyId;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public APIKey() {
    }

    public APIKey(Long apiKeyId, String apiKey, Tenant tenant, Boolean active) {
        this.apiKeyId = apiKeyId;
        this.apiKey = apiKey;
        this.tenant = tenant;
        this.active = active;
    }

    public Long getApiKeyId() {
        return apiKeyId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Boolean getActive() {
        return active;
    }

    public void setApiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
