package com.ms.payment_management_service.application.port.output;

import com.ms.payment_management_service.domain.AccountBalanceResponse;

public interface AccountServiceClient {
    AccountBalanceResponse getAccountBalance(String userId);
    void updateAccountBalance(String userId, Double amount);
}
