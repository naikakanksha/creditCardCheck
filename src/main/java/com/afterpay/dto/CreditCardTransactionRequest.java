package com.afterpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardTransactionRequest implements Serializable {

    private List<String> creditCardTransactions;

    @JsonProperty("credit_card_transactions")
    public List<String> getCreditCardTransactions() {
        return creditCardTransactions;
    }

    public void setCreditCardTransactions(List<String> creditCardTransactions) {
        this.creditCardTransactions = creditCardTransactions;
    }
}
