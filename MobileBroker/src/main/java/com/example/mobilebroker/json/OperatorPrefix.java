package com.example.mobilebroker.json;

import lombok.Data;

@Data
public class OperatorPrefix {

    private String operatorId;
    private Integer ndc;
    private Integer prefixStart;
    private Integer prefixEnd;
}
