package com.innoveller.smsbroker.utils;

import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import io.vavr.control.Either;

public class PhoneNumberNormalizer {

    public static Either<PhoneNumberInfoLookupError, String> getNationalSignificantNumber(String phone) {

        if (phone == null || phone.isBlank()) {
            return Either.left(new PhoneNumberInfoLookupError.InvalidPhoneNumber(phone,"Phone number is empty"));
        }

        phone = phone.replace("+", "").replace(" ", "").replace("-", "");  // +959254252784 -> 959254252784

        if (phone.startsWith("0")) {
            phone = "95" + phone.substring(1); // 09254252784 -> 959254252784
        }

        if (!phone.startsWith("95")) {
            return Either.left(new PhoneNumberInfoLookupError.InvalidPhoneNumber(phone,"Invalid Myanmar phone number format"));
        }

        phone = phone.substring(2); // 959254252784  ->  9254252784

        if (phone.length() < 6 || phone.length() > 10) {
            return Either.left(new PhoneNumberInfoLookupError.InvalidPhoneNumber(phone,"Invalid phone number length"));

        }

        return Either.right(phone);

    }
}