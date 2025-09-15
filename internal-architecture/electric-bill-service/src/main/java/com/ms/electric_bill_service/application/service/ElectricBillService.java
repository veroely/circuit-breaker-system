package com.ms.electric_bill_service.application.service;

import com.ms.electric_bill_service.application.port.input.ElectricBillServicePort;
import com.ms.electric_bill_service.application.port.ouput.ElectricBillRepository;
import com.ms.electric_bill_service.domain.ElectricBill;
import com.ms.electric_bill_service.dto.PaymentRequest;
import com.ms.electric_bill_service.dto.PaymentResponse;
import com.ms.electric_bill_service.infrastructure.adapter.client.PaymentServiceClient;
import com.ms.electric_bill_service.infrastructure.exceptions.PaymentProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
public class ElectricBillService implements ElectricBillServicePort {
    private final ElectricBillRepository electricBillRepository;
    private final PaymentServiceClient paymentServiceClient;

    public ElectricBillService(
            ElectricBillRepository electricBillRepository,
            PaymentServiceClient paymentServiceClient) {
        this.electricBillRepository = electricBillRepository;
        this.paymentServiceClient = paymentServiceClient;
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

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Validate the bill exists and amount matches
        ElectricBill bill = electricBillRepository.findByReference(paymentRequest.getBillReference());
        if (bill == null) {
            throw new PaymentProcessingException("Bill not found with reference: " + paymentRequest.getBillReference());
        }

        if (bill.amount().compareTo(paymentRequest.getAmount()) != 0) {
            throw new PaymentProcessingException("Payment amount does not match bill amount");
        }

        // Process payment through Payment Management Service
        PaymentResponse response = paymentServiceClient.processPayment(paymentRequest);

        if ("SUCCESS".equalsIgnoreCase(response.getStatus())) {
            // Update bill status to paid
            //ElectricBill paidBill = bill.withPaid(true);
            electricBillRepository.save(bill);
            log.info("Payment processed successfully for bill: {}", paymentRequest.getBillReference());
        } else {
            throw new PaymentProcessingException("Payment failed: " + response.getMessage());
        }

        return response;
    }
}
