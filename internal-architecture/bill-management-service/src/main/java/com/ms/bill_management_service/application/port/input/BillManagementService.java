package com.ms.bill_management_service.application.port.input;

import com.ms.bill_management_service.domain.ElectricBillRequest;
import com.ms.bill_management_service.domain.ElectricBillResponse;

public interface BillManagementService {
    ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest);
}
