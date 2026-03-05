package com.example.mobilebroker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "operator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    Operator {

    @Id
    @Column(name = "operator_id", nullable = false)
    private String operatorId;

    @Column(name = "operator_name", nullable = false)
    private String operatorName;

    @Column(name = "country_code", nullable = false)
    private Integer countryCode;
}
