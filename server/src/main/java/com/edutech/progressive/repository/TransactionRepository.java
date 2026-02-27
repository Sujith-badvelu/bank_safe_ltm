package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findByAccountId(int accountId);

}
 