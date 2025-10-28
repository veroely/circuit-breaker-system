package com.ms.account_service.service;

import com.ms.account_service.dto.AccountRequest;
import com.ms.account_service.dto.AccountResponse;
import com.ms.account_service.exception.AccountNotFoundException;
import com.ms.account_service.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse getAccountById(Long id) throws AccountNotFoundException;
    AccountResponse getAccountByNumber(String accountNumber) throws AccountNotFoundException;
    boolean verifySufficientFunds(String accountNumber, BigDecimal amount) throws AccountNotFoundException;
    void withdraw(String accountNumber, BigDecimal amount) throws AccountNotFoundException, InsufficientFundsException;
    void deposit(String accountNumber, BigDecimal amount) throws AccountNotFoundException;
    void deleteAccount(Long id) throws AccountNotFoundException;
}
