package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Loan;
@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
}