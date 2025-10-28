package com.ms.account_service.controller;

import com.ms.account_service.dto.AccountRequest;
import com.ms.account_service.dto.AccountResponse;
import com.ms.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        AccountResponse response = accountService.createAccount(accountRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}/verify-funds")
    public ResponseEntity<Boolean> verifySufficientFunds(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        boolean hasSufficientFunds = accountService.verifySufficientFunds(accountNumber, amount);
        return ResponseEntity.ok(hasSufficientFunds);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
