package com.ms.transaction_service.service.impl;

import com.ms.transaction_service.client.AccountClient;
import com.ms.transaction_service.dto.TransactionRequest;
import com.ms.transaction_service.dto.TransactionResponse;
import com.ms.transaction_service.exception.TransactionException;
import com.ms.transaction_service.mapper.TransactionMapper;
import com.ms.transaction_service.model.Transaction;
import com.ms.transaction_service.model.Transaction.TransactionStatus;
import com.ms.transaction_service.repository.TransactionRepository;
import com.ms.transaction_service.service.TransactionService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountClient accountClient;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) throws TransactionException {
        try {
            Transaction transaction = transactionMapper.toEntity(transactionRequest);
            transaction.setStatus(TransactionStatus.PENDING);
            
            Transaction savedTransaction = transactionRepository.save(transaction);
            return transactionMapper.toResponse(savedTransaction);
        } catch (Exception e) {
            log.error("Error creating transaction: {}", e.getMessage(), e);
            throw new TransactionException("Failed to create transaction: " + e.getMessage());
        }
    }

    @Override
    public void processTransaction(TransactionRequest transactionRequest) throws TransactionException {
        Transaction transaction = transactionMapper.toEntity(transactionRequest);
        transaction.setStatus(TransactionStatus.PENDING);
        
        try {
            // Save the transaction as pending first
            Transaction savedTransaction = transactionRepository.save(transaction);
            
            // Process the transaction based on its type
            switch (transaction.getType()) {
                case TRANSFER:
                    processTransfer(transaction);
                    break;
                case DEPOSIT:
                    processDeposit(transaction);
                    break;
                case WITHDRAWAL:
                    processWithdrawal(transaction);
                    break;
                default:
                    throw new TransactionException("Unsupported transaction type: " + transaction.getType());
            }
            
            // Update transaction status to completed
            savedTransaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(savedTransaction);
            
        } catch (FeignException e) {
            log.error("Error processing transaction: {}", e.contentUTF8(), e);
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new TransactionException("Failed to process transaction: " + e.contentUTF8());
        } catch (Exception e) {
            log.error("Error processing transaction: {}", e.getMessage(), e);
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new TransactionException("Failed to process transaction: " + e.getMessage());
        }
    }

    private void processTransfer(Transaction transaction) {
        // Verify source account has sufficient funds
        boolean hasSufficientFunds = accountClient.verifySufficientFunds(
            transaction.getSourceAccountNumber(), 
            transaction.getAmount()
        );
        
        if (!hasSufficientFunds) {
            throw new TransactionException("Insufficient funds in source account");
        }
        
        // Withdraw from source account
        accountClient.withdraw(
            transaction.getSourceAccountNumber(), 
            transaction.getAmount()
        );
        
        // Deposit to destination account
        accountClient.deposit(
            transaction.getDestinationAccountNumber(), 
            transaction.getAmount()
        );
    }
    
    private void processDeposit(Transaction transaction) {
        accountClient.deposit(
            transaction.getDestinationAccountNumber(), 
            transaction.getAmount()
        );
    }
    
    private void processWithdrawal(Transaction transaction) {
        boolean hasSufficientFunds = accountClient.verifySufficientFunds(
            transaction.getSourceAccountNumber(), 
            transaction.getAmount()
        );
        
        if (!hasSufficientFunds) {
            throw new TransactionException("Insufficient funds in account");
        }
        
        accountClient.withdraw(
            transaction.getSourceAccountNumber(), 
            transaction.getAmount()
        );
    }

    @Override
    public TransactionResponse getTransactionById(Long id) throws TransactionException {
        return transactionRepository.findById(id)
                .map(transactionMapper::toResponse)
                .orElseThrow(() -> new TransactionException("Transaction not found with id: " + id));
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository
                .findBySourceAccountNumberOrDestinationAccountNumberOrderByTransactionDateDesc(accountNumber, accountNumber)
                .stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelTransaction(Long transactionId) throws TransactionException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionException("Transaction not found with id: " + transactionId));
                
        if (transaction.getStatus() != TransactionStatus.COMPLETED) {
            throw new TransactionException("Only completed transactions can be cancelled");
        }
        
        // Reverse the transaction
        try {
            switch (transaction.getType()) {
                case TRANSFER:
                    // Return money from destination to source
                    accountClient.transfer(
                        transaction.getDestinationAccountNumber(),
                        transaction.getSourceAccountNumber(),
                        transaction.getAmount()
                    );
                    break;
                case DEPOSIT:
                    // Withdraw the deposited amount
                    accountClient.withdraw(
                        transaction.getDestinationAccountNumber(),
                        transaction.getAmount()
                    );
                    break;
                case WITHDRAWAL:
                    // Return the withdrawn amount
                    accountClient.deposit(
                        transaction.getSourceAccountNumber(),
                        transaction.getAmount()
                    );
                    break;
            }
            
            transaction.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(transaction);
            
        } catch (Exception e) {
            log.error("Error cancelling transaction: {}", e.getMessage(), e);
            throw new TransactionException("Failed to cancel transaction: " + e.getMessage());
        }
    }
}
