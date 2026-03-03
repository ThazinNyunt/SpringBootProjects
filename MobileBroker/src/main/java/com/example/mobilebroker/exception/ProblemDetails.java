package com.example.mobilebroker.exception;

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
