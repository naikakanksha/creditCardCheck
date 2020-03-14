package com.afterpay.controller;

import com.afterpay.api.CreditCardApi;
import com.afterpay.dto.ApiResponse;
import com.afterpay.dto.CreditCardTransactionRequest;
import com.afterpay.dto.CreditCardTransactionResponse;
import com.afterpay.exception.ApiException;
import com.afterpay.transformer.CreditCardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CreditCardController implements CreditCardApi {

    @Autowired
    private CreditCardTransformer creditCardTransformer;

    public ApiResponse findFraudCreditCards(@RequestHeader("day") String day,
                                            @RequestHeader("threshold") String threshold,
                                            @RequestBody CreditCardTransactionRequest creditCardTransactionRequest,
                                            HttpServletResponse response) throws Exception {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            List<String> fraudsCreditCards = creditCardTransformer.findFraudTransactions(
                    creditCardTransactionRequest.getCreditCardTransactions(), day, threshold);

            CreditCardTransactionResponse creditCardTransactionResponse = new CreditCardTransactionResponse();
            creditCardTransactionResponse.setFraudCreditCardNumbers(fraudsCreditCards);

            return creditCardTransactionResponse;
        } catch (ApiException ae) {
            return new ApiResponse(ae.getMessage());
        }
    }

}
