package com.afterpay.validation;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CreditCardValidatorTest {

    private CreditCardValidator cardValidator = new CreditCardValidator();

    @Test
    public void shouldFailCreditCardValidation() throws Exception {

        assertFalse(cardValidator.isValidCreditCard("123465tghjt"));

        assertFalse(cardValidator.isValidCreditCard(""));

        assertFalse(cardValidator.isValidCreditCard(null));
    }

    @Test
    public void shouldPassCreditCardValidation() {
        assertTrue(cardValidator.isValidCreditCard("10d7ce2f43e35fa57d1bbf8b1e2"));
    }
}
