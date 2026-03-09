package com.example.mobilebroker.controllers.api;

import com.example.mobilebroker.controllers.api.dtos.APIKeyClientUpdateRequest;
import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;
import com.example.mobilebroker.controllers.api.dtos.APIKeyStatusUpdateRequest;
import com.example.mobilebroker.service.APIKeyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-key")
public class APIKeyController {

    private final APIKeyService apiKeyService;

    public APIKeyController(APIKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/generate")
    public APIKeyResponse generateApiKey(@RequestParam String client) {
        return apiKeyService.createApiKey(client);
    }

    @GetMapping
    public APIKeyResponse getApiKey(@RequestParam String client) {
        return apiKeyService.getByClientName(client);
    }

    @PutMapping("/update")
    public APIKeyResponse updateApiKey(@RequestBody APIKeyClientUpdateRequest request) {
        return apiKeyService.updateApiKey(request.getClientName(), request.getNewClientName());
    }

    @PatchMapping("/status")
    public APIKeyResponse updateStatus(@RequestBody APIKeyStatusUpdateRequest request) {
        return apiKeyService.updateStatus(request.getClientName(), request.getActive());
    }

    @DeleteMapping
    public String deleteApiKey(@RequestParam String client) {
        apiKeyService.deleteApiKey(client);
        return "API key deleted successfully";
    }

}
