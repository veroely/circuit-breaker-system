package com.ms.payment_management_service.application.service;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.application.port.output.ElectricBillRepository;
import com.ms.payment_management_service.domain.ElectricBillRequest;
import com.ms.payment_management_service.domain.ElectricBillResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final ElectricBillRepository electricBillRepository;

    public PaymentServiceImpl(ElectricBillRepository electricBillRepository) {
        this.electricBillRepository = electricBillRepository;
    }

    @Override
    public ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest) {
        return electricBillRepository.getBill(electricBillRequest.idService(), electricBillRequest.referenceNumber());
    }
}
