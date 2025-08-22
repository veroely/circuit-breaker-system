package com.ms.electric_bill_service.infrastructure.adapter.rest;

import com.ms.electric_bill_service.application.port.input.ElectricBillServicePort;
import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.infrastructure.adapter.mapper.ElectricMapper;
import com.ms.electric_bill_service.dto.ElectricBillResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/electricity")
public class ElectricBillController {
    private final ElectricBillServicePort electricBillService;
    private final ElectricMapper mapper;

    public ElectricBillController(ElectricBillServicePort electricBillService, ElectricMapper mapper) {
        this.electricBillService = electricBillService;
        this.mapper = mapper;
    }

    @GetMapping("/{providerId}/bill/{referenceNumber}")
    public ElectricBillResponse getBill(
            @PathVariable String providerId,
            @PathVariable String referenceNumber) {
        ElectricBill bill = electricBillService.getBillDetails(providerId, referenceNumber);
        return mapper.toElectricBillResponse(bill);
    }
}
