package com.ms.transaction_service.repository;

import com.ms.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountNumberOrDestinationAccountNumberOrderByTransactionDateDesc(
            String sourceAccountNumber, String destinationAccountNumber);
    
    List<Transaction> findBySourceAccountNumber(String accountNumber);
    
    List<Transaction> findByDestinationAccountNumber(String accountNumber);
}
