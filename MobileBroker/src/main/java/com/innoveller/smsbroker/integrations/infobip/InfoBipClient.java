package com.innoveller.smsbroker.integrations.infobip;

import com.innoveller.smsbroker.integrations.infobip.dtos.InfoBipRequest;
import com.innoveller.smsbroker.integrations.infobip.dtos.InfoBipResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InfoBipClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${infobip.api.baseUrl}")
    private String apiUrl;

    @Value("${infobip.api.encodedAuth}")
    private String encodedAuth;

    @Value("${infobip.api.name}")
    private String providerName;

    public ResponseEntity<InfoBipResponse> sendSms(InfoBipRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", "Basic " + encodedAuth);

        HttpEntity<InfoBipRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, InfoBipResponse.class);
    }

    public String getProviderName() {
        return providerName;
    }


}
