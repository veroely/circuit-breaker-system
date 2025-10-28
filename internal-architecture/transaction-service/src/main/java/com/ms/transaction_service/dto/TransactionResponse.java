package com.ms.transaction_service.dto;

import com.ms.transaction_service.model.Transaction.TransactionStatus;
import com.ms.transaction_service.model.Transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime transactionDate;
    private String description;
}
