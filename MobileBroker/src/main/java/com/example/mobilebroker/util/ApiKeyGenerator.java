package com.example.mobilebroker.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator {

    public static String generate() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
