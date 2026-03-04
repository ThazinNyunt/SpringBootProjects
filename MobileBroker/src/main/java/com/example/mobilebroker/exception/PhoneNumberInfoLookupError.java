package com.example.mobilebroker.exception;

public sealed interface PhoneNumberInfoLookupError permits
        PhoneNumberInfoLookupError.InvalidPhoneNumber,
        PhoneNumberInfoLookupError.NdcNotFound,
        PhoneNumberInfoLookupError.OperatorNotFound {

    record InvalidPhoneNumber(String phoneNumber, String message) implements PhoneNumberInfoLookupError {};
    record NdcNotFound(String phoneNumber) implements PhoneNumberInfoLookupError {};
    record OperatorNotFound(String phoneNumber) implements PhoneNumberInfoLookupError {};
}

