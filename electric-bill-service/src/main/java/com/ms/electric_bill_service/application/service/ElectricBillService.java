package com.ms.electric_bill_service.application.service;

import com.ms.electric_bill_service.application.port.input.ElectricBillServicePort;
import com.ms.electric_bill_service.application.port.ouput.ElectricBillRepository;
import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.domain.exception.BillNotFoundException;
import com.ms.electric_bill_service.domain.exception.ElectricBillServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ElectricBillService implements ElectricBillServicePort {
    private final ElectricBillRepository electricBillRepository;

    public ElectricBillService(
            ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    //@CircuitBreaker(name = "getBill", fallbackMethod = "getBillFallback")
    @Override
    public ElectricBill getBill(String providerId, String referenceNumber) {
        try {
            return electricBillRepository.findByReference(referenceNumber);
        } catch (BillNotFoundException e) {
            throw e;
        } catch (ElectricBillServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ElectricBillServiceException("Unexpected error processing bill", e);
        }
    }

    public ElectricBill getBillFallback(String referenceNumber, Exception e) {
        if (e instanceof BillNotFoundException) {
            return null; // O algún otro valor que indique que la factura no existe
        }
        return new ElectricBill(
                referenceNumber,
                BigDecimal.ZERO,
                java.time.LocalDate.now(),
                null,
                null
        );
    }
}
