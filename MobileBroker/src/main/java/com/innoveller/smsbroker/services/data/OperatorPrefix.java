package com.innoveller.smsbroker.services.data;

public record OperatorPrefix (
        String operator,
        Integer ndc,
        Integer prefixStart,
        Integer prefixEnd ) {


}
