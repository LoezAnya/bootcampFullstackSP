package com.reto.backend.controller;

import com.reto.backend.entity.Account;
import com.reto.backend.entity.Client;
import com.reto.backend.security.jwt.JwtProvider;
import com.reto.backend.service.AccountService;
import com.reto.backend.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @Autowired
    JwtProvider jwtProvider;

    String admin = " ";

    public static String accountNumberGenerator(Account.Type type) {
        String chain = "";
        if (type.equals(Account.Type.SAVING)) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "46" + randNum;
        } else if (type.equals(Account.Type.CHECKING)) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "23" + randNum;
        }
        return chain;
    }

    @GetMapping("{clientId}")
    public ResponseEntity<List<Account>> getAllAccountByClientId(@PathVariable(value = "clientId") Long clientId) {
        if (!accountService.getAllAccountByClientId(clientId).isEmpty()) {
            return new ResponseEntity<>(accountService.getAllAccountByClientId(clientId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("{clientId}")
    public ResponseEntity<Account> createAccount(@PathVariable(value = "clientId") Long clientId, @RequestBody Account account,HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String userName= jwtProvider.getUserNameFromToken(jwtToken);
        this.admin=userName;
        Optional<Client> clientOptional = clientService.getClientById(clientId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            account.setClient(client);
            account.setAccount_state(Account.State.ACTIVE);
            account.setAccount_number(accountNumberGenerator(account.getAccount_type()));
            account.setBalance(BigDecimal.valueOf(0));
            account.setUserCreate(admin);
            account.setAvailable_balance(BigDecimal.valueOf(0));
            return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);


        }
        return new ResponseEntity<>(account, HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Account> changeState(@PathVariable(value = "id") Long id,@RequestBody Account account,HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String userName= jwtProvider.getUserNameFromToken(jwtToken);
        this.admin=userName;
        Account.State state = account.getAccount_state();

        Optional<Account> accountOptional = accountService.getAccountById(id);
        
        if (accountOptional.isPresent()) {
            
            Account account1 = accountOptional.get();
            Account.State defaultState=account1.getAccount_state();

            account1.setUser_edit(admin);
            if (state.equals(Account.State.ACTIVE)) {
                account1.setAccount_state(Account.State.INACTIVE);
                return new ResponseEntity<>(accountService.createAccount(account1), HttpStatus.CREATED);
            } else if (state.equals(Account.State.INACTIVE)) {
                account1.setAccount_state(Account.State.ACTIVE);
                return new ResponseEntity<>(accountService.createAccount(account1), HttpStatus.CREATED);
            } else if (state.equals(Account.State.CANCELED)) {
                if (BigDecimal.valueOf(1).compareTo(account1.getBalance()) >0) {
                    account1.setAccount_state(Account.State.CANCELED);
                    return new ResponseEntity<>(accountService.createAccount(account1), HttpStatus.CREATED);
                } else {
                    account1.setAccount_state(defaultState);
                    return new ResponseEntity<>(account1, HttpStatus.NOT_ACCEPTABLE);
                }
            }
        }


        return new ResponseEntity<>(account, HttpStatus.NOT_FOUND);
    }


}
