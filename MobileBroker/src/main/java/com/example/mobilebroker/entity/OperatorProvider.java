package com.example.mobilebroker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operator_provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatorProvider {

    @EmbeddedId
    private OperatorProviderId operatorProviderId;

    @ManyToOne
    @MapsId("operatorId")
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne
    @MapsId("providerId")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @Column(name = "priority", nullable = false)
    private Integer priority;

}
