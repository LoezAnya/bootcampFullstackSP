package com.reto.backend.repository;

import com.reto.backend.entity.Account;
import com.reto.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccountId(Long id);
}
