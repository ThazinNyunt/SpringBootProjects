package com.innoveller.smsbroker.services.data;

import lombok.Data;

@Data
public class OperatorPrefix {

    private String operator;
    private Integer ndc;
    private Integer prefixStart;
    private Integer prefixEnd;
}
