package com.innoveller.smsbroker.services.data;

public class OperatorPrefix {

    private String operator;
    private Integer ndc;
    private Integer prefixStart;
    private Integer prefixEnd;

    public OperatorPrefix() {
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getNdc() {
        return ndc;
    }

    public void setNdc(Integer ndc) {
        this.ndc = ndc;
    }

    public Integer getPrefixStart() {
        return prefixStart;
    }

    public void setPrefixStart(Integer prefixStart) {
        this.prefixStart = prefixStart;
    }

    public Integer getPrefixEnd() {
        return prefixEnd;
    }

    public void setPrefixEnd(Integer prefixEnd) {
        this.prefixEnd = prefixEnd;
    }
}
