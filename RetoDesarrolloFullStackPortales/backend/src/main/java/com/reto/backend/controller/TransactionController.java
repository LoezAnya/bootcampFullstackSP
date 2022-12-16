package com.reto.backend.controller;

import com.reto.backend.entity.Account;
import com.reto.backend.entity.Transaction;
import com.reto.backend.service.AccountService;
import com.reto.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients/{clientId}/accounts")
public class TransactionController {
    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getAllTransaction(@PathVariable(value = "accountId") Long id) {
        if (!transactionService.getAllTransactionByAccountId(id).isEmpty()) {
            return new ResponseEntity<>(transactionService.getAllTransactionByAccountId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<Transaction> createTransaction(@PathVariable(value = "accountId") Long id,
                                                         @RequestBody Transaction transaction) {
        Transaction sender;
        Transaction receiver = new Transaction();
        Date editDate = Date.from(Instant.now());
        if (transaction.getTransaction_type().toLowerCase().equals("consignacion")) {
            sender = accountService.getAccountById(id).map(account -> {
                BigDecimal balance = new BigDecimal(String.valueOf(account.getAvailable_balance()));
                account.setAvailable_balance(balance.add(transaction.getTransaction_value()));
                account.setBalance(account.getAvailable_balance());
                account.setModification_date(editDate);
                transaction.setAvailable_balance(account.getAvailable_balance());
                transaction.setAccount(account);
                transaction.setGmf(BigDecimal.valueOf(0));
                transaction.setMovement_type("credito");
                return transactionService.createTransaction(transaction);
            }).orElseThrow(() -> new RuntimeException("Not found" + id));
        }
        if (transaction.getTransaction_type().toLowerCase().equals("retiro")) {

            Optional<Account> opcAccount = accountService.getAccountById(id);
            if (opcAccount.isPresent()) {
                Account account = opcAccount.get();
                BigDecimal balance = new BigDecimal(String.valueOf(account.getAvailable_balance()));

                if (account.getAccount_type().toLowerCase().equals("corriente")) {
                    if (balance.subtract(transaction.getTransaction_value()).compareTo(BigDecimal.valueOf(-3000000)) < 0) {
                        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                    } else {
                        account.setAvailable_balance(balance.subtract(transaction.getTransaction_value()));
                        account.setBalance(account.getAvailable_balance());
                        account.setModification_date(editDate);
                        transaction.setAvailable_balance(account.getAvailable_balance());
                        transaction.setAccount(account);
                        transaction.setGmf(BigDecimal.valueOf(0));
                        transaction.setMovement_type("debito");
                        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
                    }
                } else if (account.getAccount_type().toLowerCase().equals("ahorro")) {
                    if (balance.subtract(transaction.getTransaction_value()).compareTo(BigDecimal.valueOf(0)) < 0) {
                        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                    } else {
                        account.setAvailable_balance(balance.subtract(transaction.getTransaction_value()));
                        account.setBalance(account.getAvailable_balance());
                        account.setModification_date(editDate);
                        transaction.setAvailable_balance(account.getAvailable_balance());
                        transaction.setAccount(account);
                        transaction.setGmf(BigDecimal.valueOf(0));
                        transaction.setMovement_type("debito");
                        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
                    }

                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        }

        if (transaction.getTransaction_type().toLowerCase().equals("transferencia")) {
            sender = transaction;
            Optional<Account> accountSender = accountService.getAccountById(id);
            if (accountSender.isPresent()) {
                Account accountSen = accountSender.get();
                BigDecimal balance = new BigDecimal(String.valueOf(accountSen.getBalance()));
                accountSen.setBalance(balance.subtract(transaction.getTransaction_value()));
                accountSen.setAvailable_balance(accountSen.getBalance());
                accountSen.setModification_date(editDate);
                sender.setAvailable_balance(accountSen.getAvailable_balance());
                sender.setGmf(BigDecimal.valueOf(0));
                sender.setMovement_type("debito");
                sender.setAccount(accountSen);
                Optional<Account> accountReceptor = accountService.getAccountById(transaction.getId_receptor_account());
                if (accountReceptor.isPresent()) {
                    Account accountAuxRec = accountReceptor.get();
                    BigDecimal balanceRec = new BigDecimal(String.valueOf(accountAuxRec.getBalance()));
                    accountAuxRec.setBalance(balanceRec.add(transaction.getTransaction_value()));
                    accountAuxRec.setAvailable_balance(accountAuxRec.getBalance());
                    accountAuxRec.setModification_date(editDate);
                    receiver.setAvailable_balance(accountAuxRec.getAvailable_balance());
                    receiver.setGmf(BigDecimal.valueOf(0));
                    receiver.setTransaction_value(transaction.getTransaction_value());
                    receiver.setDescription(transaction.getDescription());
                    receiver.setId_sender_account(transaction.getId_sender_account());
                    receiver.setId_receptor_account(transaction.getId_receptor_account());
                    receiver.setTransaction_type(transaction.getTransaction_type());
                    receiver.setMovement_type("credito");
                    receiver.setAccount(accountAuxRec);
                }

                transactionService.createTransaction(receiver);
                return new ResponseEntity<>(transactionService.createTransaction(sender), HttpStatus.CREATED);
            }

        } else {
            return new ResponseEntity<>(transactionService.createTransaction(transaction),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

}
