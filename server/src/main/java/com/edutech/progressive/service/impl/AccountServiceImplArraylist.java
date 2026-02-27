package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Accounts;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AccountServiceImplArraylist {

    private static List<Accounts> accountsList;

    public AccountServiceImplArraylist() {
        // initialize the in-memory list
        accountsList = new ArrayList<>();
    }

    public List<Accounts> getAllAccounts() throws SQLException {
        return accountsList;
    }

    public List<Accounts> getAllAccountsSortedByBalance() throws SQLException {
        List<Accounts> copy = new ArrayList<>(accountsList);
        Collections.sort(copy); // uses Accounts.compareTo (by balance)
        return copy;
    }

    public int addAccount(Accounts accounts) throws SQLException {
        accountsList.add(accounts);
        return accountsList.size();
    }

    public void emptyArrayList() {
        accountsList = new ArrayList<>();
    }
}
