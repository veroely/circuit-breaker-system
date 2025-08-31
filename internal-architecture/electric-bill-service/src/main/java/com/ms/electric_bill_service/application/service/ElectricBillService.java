package com.ms.electric_bill_service.application.service;

import com.ms.electric_bill_service.application.port.input.ElectricBillServicePort;
import com.ms.electric_bill_service.application.port.ouput.ElectricBillRepository;
import com.ms.electric_bill_service.domain.ElectricBill;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ElectricBillService implements ElectricBillServicePort {
    private final ElectricBillRepository electricBillRepository;

    public ElectricBillService(
            ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    @CircuitBreaker(name = "getBillDetails", fallbackMethod = "alternativeOption")
    @Override
    public ElectricBill getBillDetails(String providerId, String referenceNumber) {
        return electricBillRepository.findByReference(referenceNumber);
    }

    public ElectricBill alternativeOption(String providerId, String referenceNumber) {
        return new ElectricBill(
                referenceNumber,
                BigDecimal.ZERO,
                LocalDate.now(),
                null,
                null
        );
    }

}
