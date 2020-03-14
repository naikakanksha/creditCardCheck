package com.afterpay.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validates Credit card number in hashed format
 */
@Component
public class CreditCardValidator {

    public boolean isValidCreditCard(String creditCard) {

        if (StringUtils.isBlank(creditCard)) {
            return false;
        }

        return creditCard.matches("\\p{XDigit}+");
    }
}
