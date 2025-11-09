package com.ms.electric_bill_service.infrastructure.adapter.in.rest.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
