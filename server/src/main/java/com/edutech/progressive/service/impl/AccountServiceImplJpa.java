package com.edutech.progressive.service.impl;


import com.edutech.progressive.entity.Accounts;
import com.edutech.progressive.exception.AccountNotFoundException;
import com.edutech.progressive.repository.AccountRepository;
import com.edutech.progressive.repository.TransactionRepository;
import com.edutech.progressive.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImplJpa implements AccountService {

    @Autowired
    TransactionRepository transactionRepository;

    private AccountRepository accountRepository;
    @Autowired
    public AccountServiceImplJpa(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Accounts> getAllAccounts() throws SQLException {
        return accountRepository.findAll();
    }

    @Override
    public List<Accounts> getAccountsByUser(int customerId) throws SQLException {
        return accountRepository.getAccountsByCustomerCustomerId(customerId);
    }

    @Override
    public Accounts getAccountById(int accountId) {
        Optional<Accounts> accounts = accountRepository.findById(accountId);
        if (accounts.isPresent()) {
            return accounts.get();
        }
        else {
            throw new AccountNotFoundException("No accounts found linked with this accountId : " + accountId);
        }
    }

    @Override
    public int addAccount(Accounts accounts) {
        return accountRepository.save(accounts).getAccountId();
    }

    @Override
    public void updateAccount(Accounts accounts) {
        accountRepository.save(accounts);
    }

    @Override
    public void deleteAccount(int accountId) {
        transactionRepository.deleteByAccountId(accountId);
        accountRepository.deleteById(accountId);
    }

    @Override
    public List<Accounts> getAllAccountsSortedByBalance() throws SQLException {
        List<Accounts> sortedAccounts = getAllAccounts();
        sortedAccounts.sort(Comparator.comparingDouble(Accounts::getBalance)); // Sort by account balance
        return sortedAccounts;
    }
}

