package com.afterpay.validation;

import com.afterpay.CreditCardTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@ContextConfiguration(classes = {CreditCardTransactionValidationTest.CreditCardTransactionValidationTestConfig.class})
@RunWith(SpringRunner.class)
public class CreditCardTransactionValidationTest {

    @Autowired
    private CreditCardTransactionValidation creditCardTransactionValidation;

    @Test
    public void shouldFailCreditCardTransactionValidation() {

        assertFalse(creditCardTransactionValidation.isValidCreditCardTransaction(
                createCreditCardTransaction("89wert", "2014-04-29T13:15:54", new BigDecimal(10.2))));

        assertFalse(creditCardTransactionValidation.isValidCreditCardTransaction(
                createCreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", "2014-04-29T", new BigDecimal(10.2))));

        assertFalse(creditCardTransactionValidation.isValidCreditCardTransaction(
                createCreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", "2014-04-29T13:15:54", null)));

    }

    @Test
    public void shouldPassCreditCardTransactionValidation() {

        assertTrue(creditCardTransactionValidation.isValidCreditCardTransaction(
                createCreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", "2014-04-29T13:15:54", new BigDecimal(10.2))));
    }

    private CreditCardTransaction createCreditCardTransaction(String cc, String time, BigDecimal price) {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setCreditCardNumber(cc);
        creditCardTransaction.setTimestamp(time);
        creditCardTransaction.setPrice(price);

        return creditCardTransaction;
    }

    @Configuration
    public static class CreditCardTransactionValidationTestConfig {

        @Bean
        public CreditCardTransactionValidation creditCardTransactionValidation() {
            return new CreditCardTransactionValidation();
        }

        @Bean
        public CreditCardValidator creditCardValidator() {
            return new CreditCardValidator();
        }

        @Bean
        public TimestampValidator timestampValidator() {
            return new TimestampValidator();
        }
    }

}
