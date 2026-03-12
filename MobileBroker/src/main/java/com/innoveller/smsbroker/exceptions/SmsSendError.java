package com.innoveller.smsbroker.exceptions;

public sealed interface SmsSendError extends SmsError permits
    SmsSendError.FailedNoProvider,
    SmsSendError.FailedSenderNameNotConfigured,
    SmsSendError.FailedProviderError {

    record FailedNoProvider(String operator) implements SmsSendError {}
    record FailedSenderNameNotConfigured(Long tenantId) implements  SmsSendError {}
    record FailedProviderError(String operator) implements SmsSendError {}
}
