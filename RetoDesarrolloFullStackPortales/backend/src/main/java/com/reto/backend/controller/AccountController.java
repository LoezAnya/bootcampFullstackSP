package com.reto.backend.controller;

import com.reto.backend.entity.Account;
import com.reto.backend.service.AccountService;
import com.reto.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api/clients")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;
    public static String accountNumberGenerator(String type) {
        String chain="";
        if (type.toLowerCase().equals("ahorro")) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "46" + randNum;
        } else if (type.toLowerCase().equals("corriente")) {
            Random ran = new Random();
            int randNum = ran.nextInt(99999999) + 10000000;
            return chain = "23" + randNum;
        }
        return chain;
    }
    String user="Admin";
    @GetMapping("{clientId}/accounts")
    public ResponseEntity<List<Account>> getAllAccountByClientId(@PathVariable(value ="clientId") Long clientId){
            if(!accountService.getAllAccountByClientId(clientId).isEmpty()){
                return new ResponseEntity<>(accountService.getAllAccountByClientId(clientId), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

    }


    @PostMapping("{clientId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable(value ="clientId") Long clientId, @RequestBody Account account){
        Account account1=clientService.getClientById(clientId).map(client -> {

            account.setClient(client);
            account.setAccount_state("activa");
            account.setAccount_number(accountNumberGenerator(account.getAccount_type()));
            account.setBalance(BigDecimal.valueOf(0));
            account.setUserCreate(user);
            account.setAvailable_balance(BigDecimal.valueOf(0));
            return accountService.createAccount(account);
        }).orElseThrow(() -> new RuntimeException("Not found " + clientId));
        return new ResponseEntity<>(account1, HttpStatus.CREATED);
    }
}
