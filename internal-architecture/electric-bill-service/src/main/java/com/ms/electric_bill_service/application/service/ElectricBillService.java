package com.ms.electric_bill_service.application.service;

import com.ms.electric_bill_service.application.port.input.ElectricBillServicePort;
import com.ms.electric_bill_service.application.port.ouput.ElectricBillRepository;
import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.domain.exception.BillNotFoundException;
import com.ms.electric_bill_service.domain.exception.ElectricBillServiceException;
import org.springframework.stereotype.Service;

@Service
public class ElectricBillService implements ElectricBillServicePort {
    private final ElectricBillRepository electricBillRepository;

    public ElectricBillService(
            ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    @Override
    public ElectricBill getBillDetails(String providerId, String referenceNumber) {
        try {
            return electricBillRepository.findByReference(referenceNumber);
        } catch (BillNotFoundException | ElectricBillServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ElectricBillServiceException("Unexpected error processing bill", e);
        }
    }
}
