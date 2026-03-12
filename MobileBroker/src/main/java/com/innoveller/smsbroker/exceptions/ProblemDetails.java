package com.innoveller.smsbroker.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemDetails {

    private String type;
    private String title;
    private Integer status;
    private String detail;
}
