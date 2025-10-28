package com.ms.electric_bill_service.application.port.input;

import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.dto.PaymentRequest;
import com.ms.electric_bill_service.dto.PaymentResponse;

public interface ElectricBillServicePort {
    ElectricBill getBillDetails(String providerId, String referenceNumber);
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
