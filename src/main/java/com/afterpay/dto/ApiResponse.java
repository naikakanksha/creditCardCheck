package com.afterpay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(Include.NON_NULL)
public class ApiResponse {

    private static final Logger LOG = LoggerFactory.getLogger(ApiResponse.class);

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse() {
        // TODO Auto-generated constructor stub
    }

    public ApiResponse(String message) {
       this.message = message;
    }

    @Override
    public String toString() {
        String responseAsJson = null;
        try {
            responseAsJson = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Unable to represent ApiResponse as json", e);
            return super.toString();
        }
        return responseAsJson;
    }

}
