package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.exception.CustomerAlreadyExistsException;
import com.edutech.progressive.service.impl.CustomerServiceImplArraylist;
import com.edutech.progressive.service.impl.CustomerServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImplJpa customerServiceJpa;

    @Autowired
    private CustomerServiceImplArraylist customerServiceArraylist;

    @GetMapping
    public ResponseEntity<List<Customers>> getAllCustomers() {
        try {
            List<Customers> customers = customerServiceJpa.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable int customerId) {
        try {
            Customers customers = customerServiceJpa.getCustomerById(customerId);
            if (customers != null) {
                return new ResponseEntity<>(customers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


@PostMapping
@PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')") // ✅ covers both JWT ('ADMIN') and WithMockUser('ROLE_ADMIN')
public ResponseEntity<?> addCustomer(@RequestBody Customers customers) {
    try {
        int customerId = customerServiceJpa.addCustomer(customers);
        return new ResponseEntity<>(customerId, HttpStatus.CREATED);
    } catch (CustomerAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (SQLException e) {
        return new ResponseEntity<>("Unable to process your request at the moment",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId, @RequestBody Customers customers) {
        try {
            customers.setCustomerId(customerId);
            customerServiceJpa.updateCustomer(customers);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Unable to process your request at the moment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) {
        try {
            customerServiceJpa.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Customers>> getAllCustomersFromArrayList() throws SQLException {
        List<Customers> customers = customerServiceArraylist.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/toArrayList")
    public ResponseEntity<Integer> addCustomersToArrayList(@RequestBody Customers customers) throws SQLException {
        int customersList = customerServiceArraylist.addCustomer(customers);
        return new ResponseEntity<>(customersList, HttpStatus.CREATED);
    }

    @GetMapping("/fromArrayList/all")
    public ResponseEntity<List<Customers>> getAllCustomersSortedByNameFromArrayList() throws SQLException {
        List<Customers> customersList = customerServiceArraylist.getAllCustomersSortedByName();
        return new ResponseEntity<>(customersList, HttpStatus.OK);
    }
}