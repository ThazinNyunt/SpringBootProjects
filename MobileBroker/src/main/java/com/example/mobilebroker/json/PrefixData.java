package com.example.mobilebroker.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PrefixData {

    private List<Ndc> ndc;

    @JsonProperty("operator_prefix")
    private List<OperatorPrefix> operatorPrefix;
}
