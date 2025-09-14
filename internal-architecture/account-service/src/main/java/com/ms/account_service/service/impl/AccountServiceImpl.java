package com.ms.account_service.service.impl;

import com.ms.account_service.dto.AccountRequest;
import com.ms.account_service.dto.AccountResponse;
import com.ms.account_service.exception.AccountNotFoundException;
import com.ms.account_service.exception.InsufficientFundsException;
import com.ms.account_service.mapper.AccountMapper;
import com.ms.account_service.model.Account;
import com.ms.account_service.repository.AccountRepository;
import com.ms.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) {
        if (accountRepository.existsByAccountNumber(accountRequest.getAccountNumber())) {
            throw new IllegalArgumentException("Account with number " + accountRequest.getAccountNumber() + " already exists");
        }
        
        Account account = accountMapper.toEntity(accountRequest);
        account.setActive(true);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifySufficientFunds(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return account.hasSufficientFunds(amount);
    }

    @Override
    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        
        if (!account.hasSufficientFunds(amount)) {
            throw new InsufficientFundsException("Insufficient funds in account: " + accountNumber);
        }
        
        account.withdraw(amount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        
        account.deposit(amount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}
