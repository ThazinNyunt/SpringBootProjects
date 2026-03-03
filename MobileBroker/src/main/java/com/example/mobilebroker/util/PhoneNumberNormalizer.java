package com.example.mobilebroker.util;

import com.example.mobilebroker.exception.InvalidPhoneNumberException;

public class PhoneNumberNormalizer {

    public static String normalizeToNsn(String phone) {

        if (phone == null || phone.isBlank()) {
            throw new InvalidPhoneNumberException("Phone number is empty");
        }

        phone = phone.replace("+", "").replace(" ", "").replace("-", "");  // +959254252784 -> 959254252784

        if (phone.startsWith("0")) {
            phone = "95" + phone.substring(1); // 09254252784 -> 959254252784
        }

        if (!phone.startsWith("95")) {
            throw new InvalidPhoneNumberException("Invalid Myanmar Phone Number Format");
        }

        phone = phone.substring(2); // 95254252784

        if (phone.length() < 7 || phone.length() > 10) {
            throw new InvalidPhoneNumberException("Invalid phone number length");
        }

        return phone;
    }
}