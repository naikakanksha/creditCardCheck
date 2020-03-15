package com.afterpay.api;

import com.afterpay.dto.ApiResponse;
import com.afterpay.dto.CreditCardTransactionRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

public interface CreditCardApi {

    @RequestMapping(
            value = {"/find"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    ApiResponse findFraudCreditCards(String day, String threshold,
                                     CreditCardTransactionRequest creditCardTransactionRequest,
                                     HttpServletResponse response) throws Exception ;
}
