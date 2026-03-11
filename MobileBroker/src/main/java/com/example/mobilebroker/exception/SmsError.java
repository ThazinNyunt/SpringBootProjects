package com.example.mobilebroker.exception;

public sealed interface SmsError permits
    PhoneNumberInfoLookupError,
    SmsSendError {
}
