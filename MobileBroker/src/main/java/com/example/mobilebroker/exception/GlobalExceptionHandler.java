package com.example.mobilebroker.exception;

import com.example.mobilebroker.error.OperatorError;
import io.vavr.control.Either;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Either.class.isAssignableFrom(returnType.getParameterType());
    }

    // Either
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Either<?, ?> either) {
            if (either.isRight()) {
                return either.get();
            }
            OperatorError error = (OperatorError) either.getLeft();
            String uri = ((ServletServerHttpRequest) request).getServletRequest().getRequestURI();
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            if(error instanceof OperatorError.InvalidPhoneNumber e) {
                return ProblemDetails.builder()
                        .type("https//example.com/problem/invalid-phone-number")
                        .title("Invalid Phone Number")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("No operator found for: " + e.phoneNumber())
                        .instance(uri)
                        .build();
            }
        }
        return body;
    }

    // Exception
    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<ProblemDetails> handleInvalidPhone(InvalidPhoneNumberException ex, HttpServletRequest request) {
        ProblemDetails problem = ProblemDetails.builder()
                .type("https://example.com/problems/invalid-phone-number")
                .title("Invalid Phone Number")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .build();

        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleSystemError(Exception ex, HttpServletRequest request) {
        ProblemDetails problem = ProblemDetails.builder()
                .type("https://example.com/problems/internal-server-error")
                .title("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail("Unexpected system error occured.")
                .instance(request.getRequestURI())
                .build();
        return  new ResponseEntity<>(problem, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
