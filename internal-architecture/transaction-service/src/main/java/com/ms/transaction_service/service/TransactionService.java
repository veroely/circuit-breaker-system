package com.ms.transaction_service.service;

import com.ms.transaction_service.dto.TransactionRequest;
import com.ms.transaction_service.dto.TransactionResponse;
import com.ms.transaction_service.exception.TransactionException;

import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest) throws TransactionException;
    TransactionResponse getTransactionById(Long id) throws TransactionException;
    List<TransactionResponse> getTransactionsByAccountNumber(String accountNumber);
    void processTransaction(TransactionRequest transactionRequest) throws TransactionException;
    void cancelTransaction(Long transactionId) throws TransactionException;
}
