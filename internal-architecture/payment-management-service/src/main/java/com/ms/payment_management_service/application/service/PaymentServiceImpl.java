package com.ms.payment_management_service.application.service;

import com.ms.payment_management_service.application.port.input.PaymentService;
import com.ms.payment_management_service.infrastructure.adapter.input.feign.AccountServiceClient;
import com.ms.payment_management_service.domain.*;
import com.ms.payment_management_service.infrastructure.adapter.input.feign.TransactionServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("Processing payment for user: {}, bill: {}", paymentRequest.getUserId(), paymentRequest.getBillId());
        
        try {
            // 1. Verify account balance
            AccountBalanceResponse accountBalance;
            try {
                accountBalance = accountServiceClient.getAccountBalance(paymentRequest.getUserId());
                
                if (accountBalance.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
                    log.warn("Insufficient funds for user: {}. Required: {}, Available: {}", 
                            paymentRequest.getUserId(), paymentRequest.getAmount(), accountBalance.getBalance());
                    return buildFailedPaymentResponse(paymentRequest, INSUFFICIENT_FUNDS);
                }
            } catch (FeignException.NotFound e) {
                log.error("Account not found for user: {}", paymentRequest.getUserId());
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found for user: " + paymentRequest.getUserId()
                );
            } catch (FeignException e) {
                log.error("Error accessing account service: {}", e.contentUTF8(), e);
                throw new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "Error accessing account service: " + e.getMessage()
                );
            }
            
            TransactionResponse transaction;
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
                
                transaction = transactionServiceClient.createTransaction(transactionRequest);
                
                // 3. Update account balance
                accountServiceClient.updateAccountBalance(
                        paymentRequest.getUserId(), 
                        paymentRequest.getAmount().negate().doubleValue()
                );
                
            } catch (FeignException e) {
                log.error("Error processing transaction: {}", e.contentUTF8(), e);
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error processing transaction: " + e.getMessage()
                );
            }
            
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
                    
        } catch (ResponseStatusException e) {
            throw e; // Re-throw already handled exceptions
        } catch (Exception e) {
            log.error("Unexpected error processing payment for user: {}, bill: {}", 
                    paymentRequest.getUserId(), paymentRequest.getBillId(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Unexpected error processing payment: " + e.getMessage()
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
