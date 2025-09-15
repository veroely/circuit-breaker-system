package com.ms.payment_management_service.infrastructure.adapter.input.rest;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.domain.PaymentRequest;
import com.ms.payment_management_service.domain.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentService.processPayment(paymentRequest);
        HttpStatus status = "COMPLETED".equals(response.getStatus()) ? 
                HttpStatus.OK : HttpStatus.BAD_REQUEST;
                
        return new ResponseEntity<>(response, status);
    }
}
