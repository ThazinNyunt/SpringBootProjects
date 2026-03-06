package com.example.MovieAPI.controllers;

import com.example.MovieAPI.output.PostOfficeResponseBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postal")
public class PostDetailsController {

    @RequestMapping(value = "/byCity", method = RequestMethod.GET,
                    consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostOfficeResponseBean getPostalByCity(@RequestParam String city) {
        return null;
    }
}
