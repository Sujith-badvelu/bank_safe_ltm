package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.exception.CustomerAlreadyExistsException;
import com.edutech.progressive.repository.AccountRepository;
import com.edutech.progressive.repository.CustomerRepository;
import com.edutech.progressive.repository.TransactionRepository;
import com.edutech.progressive.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImplJpa implements CustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;           // optional
    private TransactionRepository transactionRepository;  // optional

    // Preferred by Spring
    @Autowired
    public CustomerServiceImplJpa(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Optional full wiring
    public CustomerServiceImplJpa(CustomerRepository customerRepository,
                                  AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Customers> getAllCustomers() throws SQLException {
        return customerRepository.findAll();
    }

    @Override
    public Customers getCustomerById(int customerId) throws SQLException {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public int addCustomer(Customers customers) throws SQLException {
        if (customers.getEmail() != null) {
            Customers existing = customerRepository.findByEmail(customers.getEmail());
            if (existing != null) {
                throw new CustomerAlreadyExistsException("Email already exists for another customer");
            }
        }
        Customers saved = customerRepository.save(customers);
        return saved.getCustomerId();
    }

    @Override
    public void updateCustomer(Customers customers) throws SQLException {
        if (customers.getEmail() != null) {
            Customers existing = customerRepository.findByEmail(customers.getEmail());
            if (existing != null && existing.getCustomerId() != customers.getCustomerId()) {
                throw new CustomerAlreadyExistsException("Email already exists for another customer");
            }
        }
        customerRepository.save(customers);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Customers existing = customerRepository.findByCustomerId(customerId);
        if (existing != null) {
            customerRepository.delete(existing);
        }
    }

    @Override
    public List<Customers> getAllCustomersSortedByName() throws SQLException {
        List<Customers> list = customerRepository.findAll();
        list.sort(Comparator.comparing(Customers::getName, String.CASE_INSENSITIVE_ORDER));
        return list;
    }
}