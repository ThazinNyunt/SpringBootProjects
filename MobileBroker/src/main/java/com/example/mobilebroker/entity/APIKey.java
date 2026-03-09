package com.example.mobilebroker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_key")
@Getter
@Setter
@NoArgsConstructor
public class APIKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_key_id")
    private Long apiKeyId;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
