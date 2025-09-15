package com.ms.payment_management_service.application.port.input;

import com.ms.payment_management_service.domain.ElectricBillRequest;
import com.ms.payment_management_service.domain.ElectricBillResponse;
import com.ms.payment_management_service.domain.PaymentRequest;
import com.ms.payment_management_service.domain.PaymentResponse;

public interface PaymentService {
    ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest);
    
    /**
     * Processes a payment by validating the account balance and creating a transaction
     * @param paymentRequest the payment request containing payment details
     * @return PaymentResponse with the result of the payment processing
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
