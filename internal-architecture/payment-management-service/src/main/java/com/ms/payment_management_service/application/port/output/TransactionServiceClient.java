package com.ms.payment_management_service.application.port.output;

import com.ms.payment_management_service.domain.TransactionRequest;
import com.ms.payment_management_service.domain.TransactionResponse;

public interface TransactionServiceClient {
    TransactionResponse createTransaction(TransactionRequest request);
}
