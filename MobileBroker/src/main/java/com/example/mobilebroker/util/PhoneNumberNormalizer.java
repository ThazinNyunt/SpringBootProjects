package com.example.mobilebroker.util;
public class PhoneNumberNormalizer {

    public static String normalizeToNsn(String phone) {

        if (phone == null || phone.isBlank()) {
            return "error: Phone number is empty!";
        }

        phone = phone.replace("+", "").replace(" ", "").replace("-", "");  // +959254252784 -> 959254252784

        if (phone.startsWith("0")) {
            phone = "95" + phone.substring(1); // 09254252784 -> 959254252784
        }

        if (!phone.startsWith("95")) {
            return "error: Invalid Myanmar Phone Number Format";
        }

        phone = phone.substring(2); // 959254252784  ->  9254252784

        if (phone.length() < 6 || phone.length() > 10) {
            return "error: Invalid phone number length";
        }

        return phone;
    }
}