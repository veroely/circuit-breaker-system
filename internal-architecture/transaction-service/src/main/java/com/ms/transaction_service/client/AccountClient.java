package com.ms.transaction_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "${account.service.url}")
public interface AccountClient {
    
    @GetMapping("/api/accounts/{accountNumber}/verify-funds")
    boolean verifySufficientFunds(
        @PathVariable String accountNumber,
        @RequestParam BigDecimal amount
    );
    
    @PostMapping("/api/accounts/{accountNumber}/withdraw")
    void withdraw(
        @PathVariable String accountNumber,
        @RequestParam BigDecimal amount
    );
    
    @PostMapping("/api/accounts/{accountNumber}/deposit")
    void deposit(
        @PathVariable String accountNumber,
        @RequestParam BigDecimal amount
    );
    
    @PostMapping("/api/accounts/transfer")
    void transfer(
        @RequestParam String fromAccount,
        @RequestParam String toAccount,
        @RequestParam BigDecimal amount
    );
}
