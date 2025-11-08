package com.ms.electric_bill_service.application.port.output;

import com.ms.electric_bill_service.domain.ElectricBill;

public interface ElectricBillClientPort {
    ElectricBill findByReference(String reference);
}
