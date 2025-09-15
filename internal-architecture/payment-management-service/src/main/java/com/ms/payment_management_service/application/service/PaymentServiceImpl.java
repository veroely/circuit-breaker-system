package com.ms.payment_management_service.application.service;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.application.port.output.AccountServiceClient;
import com.ms.payment_management_service.application.port.output.TransactionServiceClient;
import com.ms.payment_management_service.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final AccountServiceClient accountServiceClient;
    private final TransactionServiceClient transactionServiceClient;
    private static final String PAYMENT_DESCRIPTION = "Electric Bill Payment";
    private static final String INSUFFICIENT_FUNDS = "Insufficient funds for the transaction";
    private static final String PAYMENT_SUCCESS = "Payment processed successfully";

    @Override
    public ElectricBillResponse getPaymentDetail(ElectricBillRequest electricBillRequest) {
        return new ElectricBillResponse("", BigDecimal.ZERO, LocalDate.now(), "", "");
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("Processing payment for user: {}, bill: {}", paymentRequest.getUserId(), paymentRequest.getBillId());
        
        // 1. Verify account balance
        AccountBalanceResponse accountBalance = accountServiceClient.getAccountBalance(paymentRequest.getUserId());
        
        if (accountBalance.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
            log.warn("Insufficient funds for user: {}. Required: {}, Available: {}", 
                    paymentRequest.getUserId(), paymentRequest.getAmount(), accountBalance.getBalance());
            return buildFailedPaymentResponse(paymentRequest, INSUFFICIENT_FUNDS);
        }
        
        try {
            // 2. Create transaction record
            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .userId(paymentRequest.getUserId())
                    .billId(paymentRequest.getBillId())
                    .amount(paymentRequest.getAmount().negate()) // Negative amount for debit
                    .type("DEBIT")
                    .description(PAYMENT_DESCRIPTION)
                    .referenceId(UUID.randomUUID().toString())
                    .build();
            
            TransactionResponse transaction = transactionServiceClient.createTransaction(transactionRequest);
            
            // 3. Update account balance
            accountServiceClient.updateAccountBalance(
                    paymentRequest.getUserId(), 
                    paymentRequest.getAmount().negate().doubleValue()
            );
            
            log.info("Payment processed successfully. Transaction ID: {}", transaction.getTransactionId());
            
            return PaymentResponse.builder()
                    .paymentId(UUID.randomUUID().toString())
                    .userId(paymentRequest.getUserId())
                    .billId(paymentRequest.getBillId())
                    .amount(paymentRequest.getAmount())
                    .status("COMPLETED")
                    .transactionId(transaction.getTransactionId())
                    .timestamp(LocalDateTime.now())
                    .message(PAYMENT_SUCCESS)
                    .build();
                    
        } catch (Exception e) {
            log.error("Error processing payment for user: {}, bill: {}", 
                    paymentRequest.getUserId(), paymentRequest.getBillId(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Error processing payment: " + e.getMessage()
            );
        }
    }
    
    private PaymentResponse buildFailedPaymentResponse(PaymentRequest request, String errorMessage) {
        return PaymentResponse.builder()
                .paymentId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .billId(request.getBillId())
                .amount(request.getAmount())
                .status("FAILED")
                .timestamp(LocalDateTime.now())
                .message(errorMessage)
                .build();
    }
}
