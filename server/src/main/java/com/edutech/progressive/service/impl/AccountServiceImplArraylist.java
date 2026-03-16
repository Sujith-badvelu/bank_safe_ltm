package com.edutech.progressive.service.impl;


import com.edutech.progressive.entity.Accounts;
import com.edutech.progressive.service.AccountService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountServiceImplArraylist implements AccountService {

    private static List<Accounts> accountsList = new ArrayList<>();

    @Override
    public List<Accounts> getAllAccountsSortedByBalance() {
        List<Accounts> sortedAccounts = accountsList;
        sortedAccounts.sort(Comparator.comparingDouble(Accounts::getBalance)); // Sort by account balance
        return sortedAccounts;
    }

    @Override
    public void emptyArrayList() {
        accountsList = new ArrayList<>();
    }

    @Override
    public List<Accounts> getAllAccounts() {
        return accountsList;
    }

    @Override
    public int addAccount(Accounts accounts) {
        accountsList.add(accounts);
        return accountsList.size();
    }
}