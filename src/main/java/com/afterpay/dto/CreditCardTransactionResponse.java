package com.afterpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardTransactionResponse extends ApiResponse implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(CreditCardTransactionResponse.class);

    private List<String> fraudCreditCardNumbers;

    @JsonProperty("fraud_credit_card_numbers")
    public List<String> getFraudCreditCardNumbers() {
        return fraudCreditCardNumbers;
    }

    public void setFraudCreditCardNumbers(List<String> fraudCreditCardNumbers) {
        this.fraudCreditCardNumbers = fraudCreditCardNumbers;
    }

    @Override
    public String toString() {
        String responseAsJson = null;
        try {
            responseAsJson = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to represent CreditCardTransactionResponse as json", e);
            return super.toString();
        }
        return responseAsJson;
    }

}
