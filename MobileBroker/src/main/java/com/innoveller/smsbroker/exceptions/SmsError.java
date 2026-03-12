package com.innoveller.smsbroker.exceptions;

public sealed interface SmsError permits
    PhoneNumberInfoLookupError,
    SmsSendError {
}
