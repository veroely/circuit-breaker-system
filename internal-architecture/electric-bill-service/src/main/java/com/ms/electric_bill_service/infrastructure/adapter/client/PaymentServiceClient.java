package com.ms.electric_bill_service.infrastructure.adapter.client;

import com.ms.electric_bill_service.dto.PaymentRequest;
import com.ms.electric_bill_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-management-service", url = "${external-services.payment-service.url}")
public interface PaymentServiceClient {
    @PostMapping("/api/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);
}
