package com.edutech.progressive.service.impl;

import com.edutech.progressive.dao.TransactionDAO;
import com.edutech.progressive.entity.Transactions;
import com.edutech.progressive.service.TransactionService;

import java.sql.SQLException;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;

    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public List<Transactions> getAllTransactions() throws SQLException {
        return transactionDAO.getAllTransactions();
    }

    @Override
    public Transactions getTransactionById(int transactionId) throws SQLException {
        return transactionDAO.getTransactionById(transactionId);
    }

    @Override
    public int addTransaction(Transactions transaction) throws SQLException {
        return transactionDAO.addTransaction(transaction);
    }

    @Override
    public void updateTransaction(Transactions transaction) throws SQLException {
        transactionDAO.updateTransaction(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId) throws SQLException {
        transactionDAO.deleteTransaction(transactionId);
    }

    @Override
    public List<Transactions> getTransactionsByCustomerId(int customerId) throws SQLException {
        return transactionDAO.getTransactionsByCustomerId(customerId);
    }
}