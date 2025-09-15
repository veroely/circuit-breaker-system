package com.ms.payment_management_service.application.port.input;

import com.ms.payment_management_service.domain.PaymentRequest;
import com.ms.payment_management_service.domain.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
