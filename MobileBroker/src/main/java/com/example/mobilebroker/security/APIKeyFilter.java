package com.example.mobilebroker.security;

import com.example.mobilebroker.entity.APIKey;
import com.example.mobilebroker.repository.APIKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class APIKeyFilter extends OncePerRequestFilter {

    private final APIKeyRepository apiKeyRepository;

    public APIKeyFilter(APIKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/api-key")) {
            filterChain.doFilter(request,response);
            return;
        }

        String apiKey = request.getHeader("x-api-key");

        if(apiKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing API Key!");
            return;
        }

        Optional<APIKey> apiKeyOptional = apiKeyRepository.findByApiKeyAndActiveTrue(apiKey);
        if(apiKeyOptional.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API Key!");
            return;
        }

        Long tenantId = apiKeyOptional.get().getTenant().getTenantId();
        request.setAttribute("tenantId", tenantId);

        filterChain.doFilter(request, response);
    }
}