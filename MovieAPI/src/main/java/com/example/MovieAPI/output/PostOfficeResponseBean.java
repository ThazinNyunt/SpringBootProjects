package com.example.MovieAPI.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class PostOfficeResponseBean {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("PostOffice")
    private List<PostOfficeDetailsBean> postOffice;
}
