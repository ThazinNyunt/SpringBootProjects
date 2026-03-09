package com.example.mobilebroker.integrations.smspoh;

import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohRequest;
import com.example.mobilebroker.integrations.smspoh.dtos.SmsPohResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SmsPohClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${smspoh.api.baseUrl}")
    private String apiUrl;

    @Value("${smspoh.api.authToken}")
    private String authToken;

    @Value("${smspoh.api.name}")
    private String providerName;

    public ResponseEntity<SmsPohResponse> sendSms(SmsPohRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        HttpEntity<SmsPohRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, SmsPohResponse.class);
    }

    public String getProviderName() {
        return providerName;
    }


}
