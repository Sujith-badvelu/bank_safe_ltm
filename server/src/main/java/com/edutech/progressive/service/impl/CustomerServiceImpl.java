package com.edutech.progressive.service.impl;

import com.edutech.progressive.dao.CustomerDAO;
import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.service.CustomerService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customers> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    @Override
    public int addCustomer(Customers customers) throws SQLException {
        return customerDAO.addCustomer(customers);
    }

    @Override
    public List<Customers> getAllCustomersSortedByName() throws SQLException {
        List<Customers> list = customerDAO.getAllCustomers();
        Collections.sort(list); // relies on Customers.compareTo (case-insensitive)
        return list;
    }

    // Updates an existing customer's details
    @Override
    public void updateCustomer(Customers customers) throws SQLException {
        customerDAO.updateCustomer(customers);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        customerDAO.deleteCustomer(customerId);
    }

    @Override
    public Customers getCustomerById(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId);
    }
}