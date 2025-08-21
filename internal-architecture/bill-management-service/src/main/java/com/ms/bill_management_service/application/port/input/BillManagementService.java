package com.ms.bill_management_service.application.port.input;

import com.ms.bill_management_service.domain.BillRequest;
import com.ms.bill_management_service.domain.BillResponse;

public interface BillManagementService {
    BillResponse getBillDetails(BillRequest billRequest);
}
