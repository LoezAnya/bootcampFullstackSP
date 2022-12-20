package com.reto.backend.service;

import com.reto.backend.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public Account createAccount(Account account);
    public List<Account> getAllAccountByClientId(Long id);

    public Optional<Account> getAccountByNumber(String number);
    public List<Account> getAllAccount();

    public Optional<Account> getAccountById(Long id);

    public boolean deleteAccountById(Long id);
}
