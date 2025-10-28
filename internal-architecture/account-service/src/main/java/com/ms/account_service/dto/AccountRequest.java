package com.ms.account_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotBlank(message = "Account type is required")
    private String accountType;
    
    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.0", message = "Balance must be greater than or equal to 0")
    private BigDecimal initialBalance;
    
    @NotBlank(message = "Owner ID is required")
    private String ownerId;
}
