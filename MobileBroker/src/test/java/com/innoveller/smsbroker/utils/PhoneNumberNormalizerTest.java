package com.innoveller.smsbroker.utils;

import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneNumberNormalizerTest {

    @Test
    void shouldReturnNsnForValidMyanmarPhoneNumber() {
        Either<PhoneNumberInfoLookupError, String> result = PhoneNumberNormalizer.getNationalSignificantNumber("+959254252784");

        assertTrue(result.isRight());
        assertEquals("9254252784", result.get());
    }

    @Test
    void shouldConvertNumberStartWithZero() {
        Either<PhoneNumberInfoLookupError, String> result = PhoneNumberNormalizer.getNationalSignificantNumber("09254252784");

        assertTrue(result.isRight());
        assertEquals("9254252784", result.get());
    }
}
