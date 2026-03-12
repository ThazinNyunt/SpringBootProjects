package com.innoveller.smsbroker.controllers.api;

import com.innoveller.smsbroker.controllers.api.dtos.APIKeyTenantRequest;
import com.innoveller.smsbroker.controllers.api.dtos.APIKeyResponse;
import com.innoveller.smsbroker.exceptions.APIKeyError;
import com.innoveller.smsbroker.exceptions.ProblemDetails;
import com.innoveller.smsbroker.services.APIKeyService;
import com.innoveller.smsbroker.services.dtos.APIKeyInfo;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-key")
public class APIKeyController {

    private final APIKeyService apiKeyService;

    public APIKeyController(APIKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/generate")
    public Either<ProblemDetails, APIKeyResponse>  generateApiKey(@RequestParam String name) {
        return apiKeyService.createApiKey(name)
                .bimap(
                        this::mapToApiError,
                        this::mapToResponse
                );
    }

    @GetMapping
    public Either<ProblemDetails, APIKeyResponse> getApiKey(@RequestParam String name) {
        return apiKeyService.getAPIKeyByName(name)
                .bimap(
                        this::mapToApiError,
                        this::mapToResponse
                );
    }

    @PutMapping("/update")
    public Either<ProblemDetails, APIKeyResponse>  updateApiKey(@RequestBody APIKeyTenantRequest request) {
        return apiKeyService.updateApiKeyAndName(request.getName(), request.getNewName())
                .bimap(
                        this::mapToApiError,
                        this::mapToResponse
                );
    }

    @PatchMapping("/{name}/status={active}")
    public Either<ProblemDetails, APIKeyResponse>  updateStatus(@PathVariable String name, @PathVariable boolean active) {
        return apiKeyService.updateAPIKeyStatus(name, active)
                .bimap(
                        this::mapToApiError,
                        this::mapToResponse
                );
    }

    @DeleteMapping
    public Either<ProblemDetails, String>  deleteApiKey(@RequestParam String name) {
        return apiKeyService.deleteApiKeyAndTenant(name)
                .bimap(
                        this::mapToApiError,
                        result -> "API key deleted successfully"
                );
    }
    private APIKeyResponse mapToResponse(APIKeyInfo result) {

        return new APIKeyResponse(
                result.tenantName(),
                result.apiKey(),
                result.active()
        );
    }

    private ProblemDetails mapToApiError(APIKeyError error) {

        if(error instanceof APIKeyError.TenantAlreadyExists e) {
            return ProblemDetails.builder()
                    .type("https://example.com/problems/api-key")
                    .title("TENANT_ALREADY_EXISTS")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail("Tenant already exists: " + e.tenantName())
                    .build();
        }
        if (error instanceof APIKeyError.TenantNotFound e) {

            return ProblemDetails.builder()
                    .type("https://example.com/problems/api-key")
                    .title("TENANT_NOT_FOUND")
                    .status(HttpStatus.NOT_FOUND.value())
                    .detail("Tenant not found: " + e.tenantName())
                    .build();
        }

        return ProblemDetails.builder()
                .type("https://example.com/problems/unknown")
                .title("UNKNOWN_ERROR")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

}
