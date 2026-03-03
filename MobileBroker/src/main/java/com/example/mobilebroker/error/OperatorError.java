package com.example.mobilebroker.error;

public sealed interface OperatorError permits OperatorError.InvalidPhoneNumber {
    record InvalidPhoneNumber(String phoneNumber) implements OperatorError {};
}

