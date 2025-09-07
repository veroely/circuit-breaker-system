package com.ms.bill_management_service.infraestructure.repository;

import com.ms.bill_management_service.application.port.output.ElectricBillRepository;
import com.ms.bill_management_service.domain.BillResponse;
import com.ms.bill_management_service.infraestructure.adapter.feign.ElectricBillClient;
import org.springframework.stereotype.Repository;

@Repository
public class ElectricBillRepositoryImpl implements ElectricBillRepository {
    private final ElectricBillClient electricBillClient;

    public ElectricBillRepositoryImpl(ElectricBillClient electricBillClient) {
        this.electricBillClient = electricBillClient;
    }

    @Override
    public BillResponse getBillDetails(String providerId, String referenceNumber) {
        return electricBillClient.getBill(providerId,referenceNumber);
    }
}