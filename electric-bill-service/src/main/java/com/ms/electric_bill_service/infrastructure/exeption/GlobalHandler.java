package com.ms.electric_bill_service.infrastructure.exeption;

import com.ms.electric_bill_service.domain.error.ErrorResponse;
import com.ms.electric_bill_service.domain.exception.BillNotFoundException;
import com.ms.electric_bill_service.domain.exception.ElectricBillServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ElectricBillServiceException.class)
    public ResponseEntity<ErrorResponse> globalError(ElectricBillServiceException ex) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ErrorResponse> globalError(BillNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }
}
