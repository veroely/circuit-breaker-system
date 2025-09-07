package com.ms.payment_management_service.application.service;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.domain.ElectricBillRequest;
import com.ms.payment_management_service.domain.ElectricBillResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest) {
        return new ElectricBillResponse("", BigDecimal.ZERO, LocalDate.now(), "", "");
    }
}
