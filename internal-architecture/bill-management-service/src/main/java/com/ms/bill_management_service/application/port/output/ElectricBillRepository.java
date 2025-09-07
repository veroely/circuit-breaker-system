package com.ms.bill_management_service.application.port.output;

import com.ms.bill_management_service.domain.BillResponse;

public interface ElectricBillRepository {
    BillResponse getBillDetails(String providerId, String referenceNumber);
}
