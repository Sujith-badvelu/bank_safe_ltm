package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Transactions;
import com.edutech.progressive.repository.AccountRepository;
import com.edutech.progressive.repository.TransactionRepository;
import com.edutech.progressive.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TransactionServiceImplJpa implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImplJpa(TransactionRepository transactionRepository,
                                     AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Transactions> getAllTransactions() throws SQLException {
        return transactionRepository.findAll();
    }

    @Override
    public Transactions getTransactionById(int transactionId) throws SQLException {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    @Override
    public int addTransaction(Transactions transaction) throws SQLException {
        Transactions saved = transactionRepository.save(transaction);
        return saved.getTransactionId();
    }

    @Override
    public void updateTransaction(Transactions transaction) throws SQLException {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId) throws SQLException {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<Transactions> getTransactionsByCustomerId(int customerId) throws SQLException {
        // Will be implemented on Day 8 with derived queries using account relation
        return List.of();
    }
}