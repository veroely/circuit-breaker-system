package com.ms.electric_bill_service.domain.exception;

public class ElectricBillServiceException extends RuntimeException {
    public ElectricBillServiceException(String message) {
        super(message);
    }

    public ElectricBillServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
