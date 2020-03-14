package com.afterpay.validation;

import com.afterpay.CreditCardTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditCardTransactionValidation {

    @Autowired
    public CreditCardValidator creditCardValidator;

    @Autowired
    public TimestampValidator timestampValidator;

    public boolean isValidCreditCardTransaction(CreditCardTransaction creditCardTransaction) {

        return (creditCardValidator.isValidCreditCard(creditCardTransaction.getCreditCardNumber())
            && timestampValidator.isTimeInPast(creditCardTransaction.getTimestamp())
            && creditCardTransaction.getPrice() != null && creditCardTransaction.getPrice().compareTo(BigDecimal.ZERO) > 0);

    }
}
