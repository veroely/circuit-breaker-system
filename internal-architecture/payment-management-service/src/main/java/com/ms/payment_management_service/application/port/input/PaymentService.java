package com.ms.payment_management_service.application.port.input;

import com.ms.payment_management_service.domain.ElectricBillRequest;
import com.ms.payment_management_service.domain.ElectricBillResponse;

public interface PaymentService {
    ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest);
}
