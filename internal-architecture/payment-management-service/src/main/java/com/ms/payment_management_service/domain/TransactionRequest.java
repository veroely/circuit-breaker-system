package com.ms.payment_management_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String userId;
    private String billId;
    private BigDecimal amount;
    private String type; // e.g., "DEBIT" or "CREDIT"
    private String description;
    private String referenceId;
}
