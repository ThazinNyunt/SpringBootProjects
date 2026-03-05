package com.example.mobilebroker.client;

import com.example.mobilebroker.client.dtos.SmsPohRequest;
import com.example.mobilebroker.client.dtos.SmsPohResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SmsPohClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${smspoh.api.url}")
    private String apiUrl;

    @Value("${smspoh.auth.token}")
    private String authToken;

    public SmsPohResponse sendSms(SmsPohRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        HttpEntity<SmsPohRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<SmsPohResponse> response =
                restTemplate.exchange(apiUrl, HttpMethod.POST, entity, SmsPohResponse.class);

        return response.getBody();
    }


}
