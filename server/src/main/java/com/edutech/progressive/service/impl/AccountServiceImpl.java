package com.edutech.progressive.service.impl;

import com.edutech.progressive.dao.AccountDAO;
import com.edutech.progressive.entity.Accounts;
import com.edutech.progressive.service.AccountService;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<Accounts> getAllAccounts() throws SQLException {
        return accountDAO.getAllAccounts();
    }

    @Override
    public Accounts getAccountById(int accountId) throws SQLException {
        return accountDAO.getAccountById(accountId);
    }

    @Override
    public int addAccount(Accounts accounts) throws SQLException {
        return accountDAO.addAccount(accounts);
    }

    @Override
    public void updateAccount(Accounts accounts) throws SQLException {
        accountDAO.updateAccount(accounts);
    }

    @Override
    public void deleteAccount(int accountId) throws SQLException {
        accountDAO.deleteAccount(accountId);
    }

    @Override
    public List<Accounts> getAllAccountsSortedByBalance() throws SQLException {
        List<Accounts> list = accountDAO.getAllAccounts();
        list.sort(Comparator.comparingDouble(Accounts::getBalance));
        return list;
    }

    @Override
    public List<Accounts> getAccountsByUser(int userId) throws SQLException {
        return accountDAO.getAllAccountsByCustomer(userId);
    }
}