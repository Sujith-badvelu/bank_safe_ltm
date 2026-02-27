package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {
    Customers findByCustomerId(int customerId);
    void deleteByCustomerId(int customerId);
}
