package com.reto.backend.service;

import com.reto.backend.entity.Transaction;

import java.util.List;

public interface ITransactionService {

    public Transaction createTransaction(Transaction transaction);

    public List<Transaction> getAllTransactionByAccountId(Long id);

}
