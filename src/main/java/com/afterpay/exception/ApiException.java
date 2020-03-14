package com.afterpay.exception;

import com.afterpay.dto.ApiResponse;

public class ApiException extends RuntimeException {

    private String httpResponseCode;
    private ApiResponse response;

    /**
     * Creates an ApiException with HTTP 500 Internal Server Error.
     * @param message :
     */
    public ApiException(String message) {
        this("505", message);
    }

    public ApiException(String httpResponseCode, String message) {
        super(message);
        this.httpResponseCode = httpResponseCode;

        response = new ApiResponse(message);
    }

    @Override
    public String toString() {
        return "ApiException{"
                + "httpResponseCode=" + httpResponseCode
                + ", response=" + response
                + '}';
    }

}
