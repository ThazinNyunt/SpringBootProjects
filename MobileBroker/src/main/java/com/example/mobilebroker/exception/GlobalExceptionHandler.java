package com.example.mobilebroker.exception;

import io.vavr.control.Either;
import org.apache.coyote.Response;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Either.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof Either<?,?> either) {
            return either.isRight() ? either.get() : either.getLeft();
        }
        return body;
    }

    @ExceptionHandler(APIKeyError.class)
    public ResponseEntity<ProblemDetails> handleApiKeyError(APIKeyError ex) {
        ProblemDetails problem = ProblemDetails.builder()
                .type("https://example.com/problems/api-key-error")
                .title("API Key Error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

}
