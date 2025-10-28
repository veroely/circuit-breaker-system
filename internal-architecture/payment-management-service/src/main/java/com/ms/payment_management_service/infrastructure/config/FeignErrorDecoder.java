package com.ms.payment_management_service.infrastructure.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    
    private final ErrorDecoder defaultErrorDecoder = new Default();
    
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, 
                    "Bad request to service: " + response.request().url()
                );
            case 404:
                return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Resource not found: " + response.request().url()
                );
            case 500:
                return new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Internal server error in service: " + response.request().url()
                );
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
