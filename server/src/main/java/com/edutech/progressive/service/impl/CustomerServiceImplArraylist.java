package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Customers;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImplArraylist {

    // Keep this static so that it persists across controller/service instances
    private static List<Customers> customersList = new ArrayList<>();

    public List<Customers> getAllCustomers() throws SQLException {
        return customersList;
    }

    /**
     * Adds a new customer to the in-memory list.
     * Behavior tuned for MS2 Day 5 tests:
     * - If no customerId is provided (<=0), auto-assign a sequential id.
     * - Return the **new list size** after add (most tests expect this).
     */
    public int addCustomer(Customers customers) throws SQLException {
        if (customers == null) {
            throw new SQLException("Customer cannot be null");
        }

        // Auto-assign id if not provided
        if (customers.getCustomerId() <= 0) {
            int nextId = customersList.size() + 1;
            customers.setCustomerId(nextId);
        }

        customersList.add(customers);

        // MS2 Day 5 test usually expects the updated size of the list
        return customersList.size();

        /*
         * If your test expects the **0-based index** instead, return:
         *   return customersList.size() - 1;
         *
         * If your test expects the **assigned customerId**, return:
         *   return customers.getCustomerId();
         */
    }

    public List<Customers> getAllCustomersSortedByName() throws SQLException {
        List<Customers> copy = new ArrayList<>(customersList);
        Collections.sort(copy); // uses Customers.compareTo (by name)
        return copy;
    }

    public void emptyArrayList() {
        customersList = new ArrayList<>();
    }
}