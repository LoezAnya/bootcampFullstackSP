package com.reto.backend.controller;

import com.reto.backend.entity.Account;
import com.reto.backend.entity.Transaction;
import com.reto.backend.security.jwt.JwtProvider;
import com.reto.backend.service.AccountService;
import com.reto.backend.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired
  AccountService accountService;

  @Autowired
  TransactionService transactionService;

  @Autowired
  JwtProvider jwtProvider;
public void makeGMF(Account account,Transaction sender){
  Transaction transactionGMF=new Transaction();
  transactionGMF.setDescription("GMF");
  transactionGMF.setId_sender_account(account.getAccountnumber());
  transactionGMF.setAccount(account);
  transactionGMF.setTransaction_date(new Date());
  transactionGMF.setTransaction_type("4 X 1000");
  transactionGMF.setMovement_type("debito");
  transactionGMF.setTransaction_value(BigDecimal.valueOf(0));
  if(account.getExtentGMF()==false){
    BigDecimal transactionValue=sender.getTransaction_value();
    BigDecimal gmf=new BigDecimal(0.004);
    BigDecimal discountGMF=transactionValue.multiply(gmf);
    transactionGMF.setTransaction_value(discountGMF);
    account.setAvailable_balance(account.getAvailable_balance().subtract(discountGMF));
  }
  transactionGMF.setAvailable_balance(account.getAvailable_balance());
  this.transactionService.createTransaction(transactionGMF);
  this.accountService.createAccount(account);
}
  @GetMapping("/{accountId}")
  public ResponseEntity<List<Transaction>> getAllTransaction(
    @PathVariable(value = "accountId") Long id
  ) {
    if (!transactionService.getAllTransactionByAccountId(id).isEmpty()) {
      return new ResponseEntity<>(
        transactionService.getAllTransactionByAccountId(id),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Transaction> createTransaction(
    @RequestBody Transaction transaction,HttpServletRequest request
  ) {
    String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String userName= jwtProvider.getUserNameFromToken(jwtToken);
    Transaction sender;
    Transaction receiver = new Transaction();
    Date editDate = Date.from(Instant.now());
    if (
      transaction.getTransaction_type().toLowerCase().equals("consignacion")
    ) {
      Optional<Account> conAccount = accountService.getAccountByNumber(
        transaction.getId_sender_account()
      );
      if (conAccount.isPresent()) {
        Account accountCon = conAccount.get();
        if (!accountCon.getAccount_state().equals(Account.State.CANCELED)) {
          sender =
            accountService
              .getAccountByNumber(transaction.getId_sender_account())
              .map(account -> {
                BigDecimal balance = new BigDecimal(
                  String.valueOf(account.getAvailable_balance())
                );
                account.setAvailable_balance(
                  balance.add(transaction.getTransaction_value())
                );
                account.setBalance(account.getAvailable_balance());
                account.setModification_date(editDate);
                account.setUser_edit(userName);
                transaction.setAvailable_balance(
                  account.getAvailable_balance()
                );
                transaction.setAccount(account);
                transaction.setGmf(BigDecimal.valueOf(0));
                transaction.setMovement_type("credito");
                return transactionService.createTransaction(transaction);
              })
              .orElseThrow(() ->
                new RuntimeException(
                  "Not found" + transaction.getId_sender_account()
                )
              );
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
      }
    }
    if (transaction.getTransaction_type().toLowerCase().equals("retiro")) {
      Optional<Account> opcAccount = accountService.getAccountByNumber(
        transaction.getId_sender_account()
      );
      if (opcAccount.isPresent()) {
        Account account = opcAccount.get();
        if (
          account.getAccount_state().equals(Account.State.INACTIVE) ||
          account.getAccount_state().equals(Account.State.CANCELED)
        ) {
          return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
          BigDecimal balance = new BigDecimal(
            String.valueOf(account.getAvailable_balance())
          );

          if (account.getAccount_type().equals(Account.Type.CHECKING)) {
            if (
              balance
                .subtract(transaction.getTransaction_value())
                .compareTo(BigDecimal.valueOf(-3000000)) <
              0
            ) {
              return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
              account.setAvailable_balance(
                balance.subtract(transaction.getTransaction_value())
              );
              account.setBalance(account.getAvailable_balance());
              account.setModification_date(editDate);
              account.setUser_edit(userName);
              transaction.setAvailable_balance(account.getAvailable_balance());
              transaction.setAccount(account);
              transaction.setGmf(BigDecimal.valueOf(0));
              transaction.setMovement_type("debito");
              
              transactionService.createTransaction(transaction); 
              makeGMF(account,transaction);             
              return new ResponseEntity<>(
                transaction,
                HttpStatus.CREATED
              );
            }
          } else if (account.getAccount_type().equals(Account.Type.SAVING)) {
            if (
              balance
                .subtract(transaction.getTransaction_value())
                .compareTo(BigDecimal.valueOf(0)) <
              0
            ) {
              return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
              account.setAvailable_balance(
                balance.subtract(transaction.getTransaction_value())
              );
              account.setBalance(account.getAvailable_balance());
              account.setModification_date(editDate);
              account.setUser_edit(userName);
              transaction.setAvailable_balance(account.getAvailable_balance());
              transaction.setAccount(account);
              transaction.setGmf(BigDecimal.valueOf(0));
              transaction.setMovement_type("debito");
              
              transactionService.createTransaction(transaction);
              makeGMF(account,transaction);
              return new ResponseEntity<>(
                transaction,
                HttpStatus.CREATED
              );
            }
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
          }
        }
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    if (
      transaction.getTransaction_type().toLowerCase().equals("transferencia")
    ) {
      sender = transaction;
      Optional<Account> accountSender = accountService.getAccountByNumber(
        transaction.getId_sender_account()
      );
      if (accountSender.isPresent()) {
        Account accountSen = accountSender.get();
        if (
          accountSen.getAccount_state().equals(Account.State.INACTIVE) ||
          accountSen.getAccount_state().equals(Account.State.CANCELED)
        ) {
          return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
          BigDecimal balance = new BigDecimal(
            String.valueOf(accountSen.getBalance())
          );
          if (accountSen.getAccount_type().equals(Account.Type.CHECKING)) {
            if (
              balance
                .subtract(transaction.getTransaction_value())
                .compareTo(BigDecimal.valueOf(-3000000)) <
              0
            ) {
              return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
              accountSen.setBalance(
                balance.subtract(transaction.getTransaction_value())
              );
              accountSen.setAvailable_balance(accountSen.getBalance());
              accountSen.setModification_date(editDate);
              accountSen.setUser_edit(userName);
              sender.setAvailable_balance(accountSen.getAvailable_balance());
              sender.setGmf(BigDecimal.valueOf(0));
              sender.setMovement_type("debito");
              sender.setAccount(accountSen);
              Optional<Account> accountReceptor = accountService.getAccountByNumber(
                transaction.getId_receptor_account()
              );
              if (accountReceptor.isPresent()) {
                Account accountAuxRec = accountReceptor.get();
                BigDecimal balanceRec = new BigDecimal(
                  String.valueOf(accountAuxRec.getBalance())
                );
                accountAuxRec.setBalance(
                  balanceRec.add(transaction.getTransaction_value())
                );
                accountAuxRec.setAvailable_balance(accountAuxRec.getBalance());
                accountAuxRec.setModification_date(editDate);
                accountAuxRec.setUser_edit(userName);
                receiver.setAvailable_balance(
                  accountAuxRec.getAvailable_balance()
                );
                receiver.setGmf(BigDecimal.valueOf(0));
                receiver.setTransaction_value(
                  transaction.getTransaction_value()
                );
                receiver.setDescription(transaction.getDescription());
                receiver.setId_sender_account(
                  transaction.getId_sender_account()
                );
                receiver.setId_receptor_account(
                  transaction.getId_receptor_account()
                );
                receiver.setTransaction_type(transaction.getTransaction_type());
                receiver.setMovement_type("credito");
                receiver.setAccount(accountAuxRec);
              } else {
                return new ResponseEntity<>(receiver, HttpStatus.NOT_FOUND);
              }

              transactionService.createTransaction(receiver);
              
              transactionService.createTransaction(sender);
              makeGMF(accountSen,sender);
              return new ResponseEntity<>(
                sender,
                HttpStatus.CREATED
              );
            }
          } else if (accountSen.getAccount_type().equals(Account.Type.SAVING)) {
            if (
              balance
                .subtract(transaction.getTransaction_value())
                .compareTo(BigDecimal.valueOf(0)) <
              0
            ) {
              return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
              accountSen.setBalance(
                balance.subtract(transaction.getTransaction_value())
              );
              accountSen.setAvailable_balance(accountSen.getBalance());
              accountSen.setModification_date(editDate);
              accountSen.setUser_edit(userName);
              sender.setAvailable_balance(accountSen.getAvailable_balance());
              sender.setGmf(BigDecimal.valueOf(0));
              sender.setMovement_type("debito");
              sender.setAccount(accountSen);
              Optional<Account> accountReceptor = accountService.getAccountByNumber(
                transaction.getId_receptor_account()
              );
              if (accountReceptor.isPresent()) {
                Account accountAuxRec = accountReceptor.get();
                BigDecimal balanceRec = new BigDecimal(
                  String.valueOf(accountAuxRec.getBalance())
                );
                accountAuxRec.setBalance(
                  balanceRec.add(transaction.getTransaction_value())
                );
                accountAuxRec.setAvailable_balance(accountAuxRec.getBalance());
                accountAuxRec.setModification_date(editDate);
                accountAuxRec.setUser_edit(userName);
                receiver.setAvailable_balance(
                  accountAuxRec.getAvailable_balance()
                );
                receiver.setGmf(BigDecimal.valueOf(0));
                receiver.setTransaction_value(
                  transaction.getTransaction_value()
                );
                receiver.setDescription(transaction.getDescription());
                receiver.setId_sender_account(
                  transaction.getId_sender_account()
                );
                receiver.setId_receptor_account(
                  transaction.getId_receptor_account()
                );
                receiver.setTransaction_type(transaction.getTransaction_type());
                receiver.setMovement_type("credito");
                receiver.setAccount(accountAuxRec);
              } else {
                return new ResponseEntity<>(receiver, HttpStatus.NOT_FOUND);
              }

              transactionService.createTransaction(receiver);
              
              transactionService.createTransaction(sender);
              makeGMF(accountSen,sender);
              return new ResponseEntity<>(
                sender,
                HttpStatus.CREATED
              );
            }
          }
        }
      } else {
        return new ResponseEntity<>(sender, HttpStatus.NOT_FOUND);
      }
    }
    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }
}
