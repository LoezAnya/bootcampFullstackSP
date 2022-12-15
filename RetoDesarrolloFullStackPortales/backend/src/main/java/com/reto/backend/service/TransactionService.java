package com.reto.backend.service;

import com.reto.backend.entity.Transaction;
import com.reto.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionByAccountId(Long id) {
        return transactionRepository.findByAccountId(id);
    }
}
