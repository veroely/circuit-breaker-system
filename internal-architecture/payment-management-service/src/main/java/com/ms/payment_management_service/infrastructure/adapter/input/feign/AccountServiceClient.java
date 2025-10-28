package com.ms.payment_management_service.infrastructure.adapter.input.feign;

import com.ms.payment_management_service.domain.AccountBalanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface AccountServiceClient {
    
    @GetMapping("/api/v1/accounts/{userId}/balance")
    AccountBalanceResponse getAccountBalance(@PathVariable("userId") String userId);
    
    @PutMapping("/api/v1/accounts/{userId}/balance")
    void updateAccountBalance(
            @PathVariable("userId") String userId, 
            @RequestParam("amount") Double amount
    );
}
