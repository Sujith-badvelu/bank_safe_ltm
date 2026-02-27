package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.repository.CustomerRepository;
import com.edutech.progressive.service.CustomerService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImplJpa implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImplJpa(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        Customers saved = customerRepository.save(customers);
        return saved.getCustomerId();
    }

    @Override
    public void updateCustomer(Customers customers) throws SQLException {
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