package com.example.mobilebroker.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
