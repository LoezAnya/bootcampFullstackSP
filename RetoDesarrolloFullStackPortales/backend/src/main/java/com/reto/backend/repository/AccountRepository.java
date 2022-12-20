package com.reto.backend.repository;

import com.reto.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByClientId(Long id);

    @Query(value = "SELECT * FROM ACCOUNTS WHERE ACCOUNT_NUMBER = ?1", nativeQuery = true)
    Optional<Account> findByNumber(String number);
}
