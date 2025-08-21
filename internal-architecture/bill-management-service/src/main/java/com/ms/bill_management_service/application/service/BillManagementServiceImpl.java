package com.ms.bill_management_service.application.service;

import com.ms.bill_management_service.application.port.input.BillManagementService;
import com.ms.bill_management_service.application.port.output.ElectricBillRepository;
import com.ms.bill_management_service.domain.ElectricBillRequest;
import com.ms.bill_management_service.domain.ElectricBillResponse;
import org.springframework.stereotype.Service;

@Service
public class BillManagementServiceImpl implements BillManagementService {
    private final ElectricBillRepository electricBillRepository;

    public BillManagementServiceImpl(ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    @Override
    public ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest) {
        return electricBillRepository.getBill(electricBillRequest.idService(), electricBillRequest.referenceNumber());
    }
}
