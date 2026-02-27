package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends JpaRepository<Accounts, Integer> {

    Accounts findByAccountId(int accountId);

    List<Accounts> findByCustomerId(int customerId);

    
    List<Accounts> findByCustomerCustomerId(int customerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Accounts acc WHERE acc.customer.customerId = :customerId")
    void deleteByCustomerIdViaRelation(@Param("customerId") int customerId);
}
