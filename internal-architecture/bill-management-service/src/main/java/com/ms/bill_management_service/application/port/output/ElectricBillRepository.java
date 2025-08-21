package com.ms.bill_management_service.application.port.output;

import com.ms.bill_management_service.domain.ElectricBillResponse;

public interface ElectricBillRepository {
    ElectricBillResponse getBill(String providerId, String referenceNumber);
}
