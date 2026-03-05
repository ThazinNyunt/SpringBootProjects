package com.example.mobilebroker.data;

import lombok.Data;

@Data
public class OperatorPrefix {

    private String operatorCode;
    private Integer ndc;
    private Integer prefixStart;
    private Integer prefixEnd;
}
