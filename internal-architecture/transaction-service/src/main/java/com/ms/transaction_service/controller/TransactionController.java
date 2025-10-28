package com.ms.transaction_service.controller;

import com.ms.transaction_service.dto.TransactionRequest;
import com.ms.transaction_service.dto.TransactionResponse;
import com.ms.transaction_service.exception.TransactionException;
import com.ms.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest) throws TransactionException {
        TransactionResponse response = transactionService.createTransaction(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/process")
    public ResponseEntity<TransactionResponse> processTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest) throws TransactionException {
        transactionService.processTransaction(transactionRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) throws TransactionException {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountNumber(
            @PathVariable String accountNumber) {
        List<TransactionResponse> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelTransaction(@PathVariable Long id) throws TransactionException {
        transactionService.cancelTransaction(id);
        return ResponseEntity.ok().build();
    }
}
