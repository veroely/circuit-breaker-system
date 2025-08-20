package com.ms.electric_bill_service.application.port.ouput;

import com.ms.electric_bill_service.domain.ElectricBill;

public interface ElectricBillRepository {
    ElectricBill findByReference(String reference);
}
