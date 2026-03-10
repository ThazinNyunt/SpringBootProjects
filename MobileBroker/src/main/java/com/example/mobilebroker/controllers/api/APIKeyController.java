package com.example.mobilebroker.controllers.api;

import com.example.mobilebroker.controllers.api.dtos.APIKeyTenantRequest;
import com.example.mobilebroker.controllers.api.dtos.APIKeyResponse;
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
    public APIKeyResponse generateApiKey(@RequestParam String name) {
        return apiKeyService.createApiKey(name);
    }

    @GetMapping
    public APIKeyResponse getApiKey(@RequestParam String name) {
        return apiKeyService.getAPIKeyByTenantName(name);
    }

    @PutMapping("/update")
    public APIKeyResponse updateApiKey(@RequestBody APIKeyTenantRequest request) {
        return apiKeyService.updateApiKeyAndTenantName(request.getName(), request.getNewName());
    }

    @PatchMapping("/{name}/status={active}")
    public APIKeyResponse updateStatus(@PathVariable String name, @PathVariable boolean active) {
        return apiKeyService.updateAPIKeyStatus(name, active);
    }

    @DeleteMapping
    public String deleteApiKey(@RequestParam String name) {
        apiKeyService.deleteApiKeyAndTenant(name);
        return "API key deleted successfully";
    }

}
