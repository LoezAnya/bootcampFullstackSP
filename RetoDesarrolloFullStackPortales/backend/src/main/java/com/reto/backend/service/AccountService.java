package com.reto.backend.service;

import com.reto.backend.entity.Account;
import com.reto.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account
        );
    }

    @Override
    public List<Account> getAllAccountByClientId(Long id) {
        return accountRepository.findByClientId(id);
    }

    @Override
    public List<Account> getAllAccount() {
        return null;
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public boolean deleteAccountById(Long id) {
        return accountRepository.findById(id).map(account -> {
            accountRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
