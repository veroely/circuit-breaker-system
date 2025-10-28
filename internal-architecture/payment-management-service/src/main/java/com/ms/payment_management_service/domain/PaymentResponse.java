package com.ms.payment_management_service.domain;

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
public class PaymentResponse {
    private String paymentId;
    private String userId;
    private String billId;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime timestamp;
    private String message;
}
