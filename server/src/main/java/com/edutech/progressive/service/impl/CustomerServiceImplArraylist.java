package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.service.CustomerService;

public class CustomerServiceImplArraylist implements CustomerService {

    List<Customers> customerList = new ArrayList<>();

    @Override
    public List<Customers> getAllCustomers() throws SQLException {
        return customerList;
    }

    @Override
    public int addCustomer(Customers customers) throws SQLException {
        customerList.add(customers);
        return customerList.size();
    }

    @Override
    public List<Customers> getAllCustomersSortedByName() throws SQLException {
        List<Customers> sortedList = new ArrayList<>(customerList);
        Collections.sort(sortedList);
        return sortedList;
    }

    public void emptyArrayList() {
        customerList.clear();
    }

}