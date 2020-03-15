package com.afterpay.transformer;

import com.afterpay.CreditCardTransaction;
import com.afterpay.exception.ApiException;
import com.afterpay.validation.CreditCardTransactionValidation;
import com.afterpay.validation.TimestampValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class CreditCardTransformer {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CreditCardTransformer.class);

    @Autowired
    private CreditCardTransactionValidation creditCardTransactionValidation;

    @Autowired
    private TimestampValidator timestampValidator;

    /**
     * Create CreditCardTransaction for a given transaction string
     * @param transaction
     * @return null if not valid
     */
    public CreditCardTransaction createValidCreditCardTransaction(String transaction) {

        if (StringUtils.isNotBlank(transaction)) {
            String[] data = transaction.split(",");
            if (data.length == 3) {
                CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
                creditCardTransaction.setCreditCardNumber(StringUtils.trim(data[0]));
                creditCardTransaction.setTimestamp(StringUtils.trim(data[1]));

                try {
                    BigDecimal price = new BigDecimal(StringUtils.trim(data[2]));
                    creditCardTransaction.setPrice(price);
                    if (creditCardTransactionValidation.isValidCreditCardTransaction(creditCardTransaction)) {
                        return creditCardTransaction;
                    }
                } catch (NumberFormatException ne) {
                    LOG.debug("transaction " + transaction + " does not include valid price.");
                    return null;
                }
            }
        }

        return null;
    }

    /**
     * Find all the fraud credit cards for the given day and given threshold value
     * @param transactions
     * @param day in yyyy-MM-dd format
     * @param threshold
     * @return
     * @throws Exception
     */
    public List<String> findFraudTransactions(List<String> transactions, String day, String strThreshold) throws Exception {

        List<String> fraudCreditCards = new ArrayList<>();

        Date date;
        BigDecimal threshold;

        try {

            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
            threshold = new BigDecimal(strThreshold);

            Map<String, BigDecimal> creditCards = computeTotalPrice(transactions, date);
            creditCards.entrySet().forEach(entry -> {
                if (entry.getValue().compareTo(threshold) > 0) {
                    fraudCreditCards.add(entry.getKey());
                }
            });

        } catch (ParseException pe) {
            throw new ApiException("Day " + day + " is not in valid format yyyy-mm-dd");
        } catch (NumberFormatException ne) {
            throw new ApiException("Threshold " + strThreshold + " is not valid number");
        }

        return fraudCreditCards;
    }

    /**
     * Compute total price for each card for given day
     * @param transactions
     * @param date
     * @return
     */
    public Map<String, BigDecimal> computeTotalPrice(List<String> transactions, Date date) {

        Map<String, BigDecimal> creditCards = new HashMap<>();
        transactions.forEach(transaction -> {
            CreditCardTransaction creditCardTransaction = createValidCreditCardTransaction(transaction);
            if (creditCardTransaction != null && timestampValidator.isTimestampForDay(creditCardTransaction.getTimestamp(), date)) {

                BigDecimal price = creditCards.get(creditCardTransaction.getCreditCardNumber());
                if (price == null) {
                    creditCards.put(creditCardTransaction.getCreditCardNumber(), creditCardTransaction.getPrice());
                } else {
                    creditCards.put(creditCardTransaction.getCreditCardNumber(), price.add(creditCardTransaction.getPrice()));
                }
            }
        });

        return creditCards;
    }
}
