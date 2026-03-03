package com.example.mobilebroker.error;

public sealed interface PhoneNumberInfoLookupError permits
        PhoneNumberInfoLookupError.InvalidPhoneNumber,
        PhoneNumberInfoLookupError.NdcNotFound,
        PhoneNumberInfoLookupError.OperatorNotFound {

    record InvalidPhoneNumber(String phoneNumber) implements PhoneNumberInfoLookupError {};
    record NdcNotFound(String phoneNumber) implements PhoneNumberInfoLookupError {};
    record OperatorNotFound(String phoneNumber) implements PhoneNumberInfoLookupError {};
}

