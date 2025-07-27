package com.ms.electric_bill_service.application.port.input;

import com.ms.electric_bill_service.domain.ElectricBill;

public interface ElectricBillServicePort {
    ElectricBill getBill(String providerId, String referenceNumber);
}
