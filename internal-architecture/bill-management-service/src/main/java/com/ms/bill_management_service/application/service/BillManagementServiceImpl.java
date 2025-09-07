package com.ms.bill_management_service.application.service;

import com.ms.bill_management_service.application.port.input.BillManagementService;
import com.ms.bill_management_service.application.port.output.ElectricBillRepository;
import com.ms.bill_management_service.domain.BillRequest;
import com.ms.bill_management_service.domain.BillResponse;
import org.springframework.stereotype.Service;

@Service
public class BillManagementServiceImpl implements BillManagementService {
    private final ElectricBillRepository electricBillRepository;

    public BillManagementServiceImpl(ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    @Override
    public BillResponse getBillDetails(BillRequest billRequest) {
        return electricBillRepository.getBillDetails(billRequest.idService(), billRequest.referenceNumber());
    }
}
