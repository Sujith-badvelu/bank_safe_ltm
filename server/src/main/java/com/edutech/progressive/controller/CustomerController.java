package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.entity.Transactions;
import com.edutech.progressive.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService; // JPA-backed via interface

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // -------------------- JPA Endpoints --------------------
    @GetMapping
    public ResponseEntity<List<Customers>> getAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.getAllCustomers());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable int customerId) {
        try {
            Customers c = customerService.getCustomerById(customerId);
            return ResponseEntity.ok(c);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Day 6 test expects HTTP 201 (Created) for POST /customers
     */
    @PostMapping
    public ResponseEntity<Integer> addCustomer(@RequestBody Customers customers) {
        try {
            int id = customerService.addCustomer(customers);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable int customerId, @RequestBody Customers customers) {
        try {
            customers.setCustomerId(customerId);
            customerService.updateCustomer(customers);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Placeholder for later days
    @GetMapping("/{customerId}/transactions")
    public ResponseEntity<List<Transactions>> getAllTransactionsByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}