package com.afterpay.transformer;

import com.afterpay.CreditCardTransaction;
import com.afterpay.validation.CreditCardTransactionValidation;
import com.afterpay.validation.CreditCardValidator;
import com.afterpay.validation.TimestampValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {CreditCardTransformerTest.CreditCardTransformerTestConfig.class})
@RunWith(SpringRunner.class)
public class CreditCardTransformerTest {

    @Autowired
    private CreditCardTransformer creditCardTransformer;

    @Autowired
    private CreditCardTransactionValidation creditCardTransactionValidation;

    @Autowired
    private TimestampValidator timestampValidator;

    @Test
    public void shouldCreateValidCreditCardTransaction() {

        String transaction = "10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00";
        CreditCardTransaction creditCardTransaction = creditCardTransformer.createValidCreditCardTransaction(transaction);

        assertNotNull(creditCardTransaction);
        assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", creditCardTransaction.getCreditCardNumber());
        assertEquals("2017-04-29T13:15:54", creditCardTransaction.getTimestamp());
        assertEquals("10.00", creditCardTransaction.getPrice().toString());
    }

    @Test
    public void shouldNotCreateValidCreditCardTransaction() {

        String transaction = "10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, abc";
        CreditCardTransaction creditCardTransaction = creditCardTransformer.createValidCreditCardTransaction(transaction);

        assertNull(creditCardTransaction);
    }

    @Test
    public void shouldFindFraudTransactions() throws Exception {

        List<String> transactions = Arrays.asList("10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "12d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "13d7ce2f43e35fa57d1bbf8b1e2, 2017-04-28T13:15:54, 10.00");

        List<String> fraudCreditCards = creditCardTransformer.findFraudTransactions(transactions, "2017-04-29", "10.0");

        assertNotNull(fraudCreditCards);
        assertEquals(1, fraudCreditCards.size());
        assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", fraudCreditCards.get(0));

        //scenario2
        fraudCreditCards = creditCardTransformer.findFraudTransactions(transactions, "2017-04-29", "1.0");

        assertNotNull(fraudCreditCards);
        assertEquals(2, fraudCreditCards.size());
        assertTrue(fraudCreditCards.contains("10d7ce2f43e35fa57d1bbf8b1e2"));
        assertTrue(fraudCreditCards.contains("12d7ce2f43e35fa57d1bbf8b1e2"));
        assertFalse(fraudCreditCards.contains("13d7ce2f43e35fa57d1bbf8b1e2"));
    }

    @Test
    public void shouldNotFindFraudTransactions() throws Exception {

        List<String> transactions = Arrays.asList("10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "10d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "12d7ce2f43e35fa57d1bbf8b1e2, 2017-04-29T13:15:54, 10.00",
                "13d7ce2f43e35fa57d1bbf8b1e2, 2017-04-28T13:15:54, 10.00");

        List<String> fraudCreditCards = creditCardTransformer.findFraudTransactions(transactions, "2017-04-29", "30.0");
        assertTrue(fraudCreditCards.isEmpty());

        //for different date
        fraudCreditCards = creditCardTransformer.findFraudTransactions(transactions, "2017-04-20", "30.0");
        assertTrue(fraudCreditCards.isEmpty());
    }

    @Configuration
    public static class CreditCardTransformerTestConfig {

        @Bean
        public CreditCardTransformer creditCardTransformer() {
            return new CreditCardTransformer();
        }

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
