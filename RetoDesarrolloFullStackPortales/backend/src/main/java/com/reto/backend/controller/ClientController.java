package com.reto.backend.controller;

import com.reto.backend.entity.Account;
import com.reto.backend.entity.Client;
import com.reto.backend.service.AccountService;
import com.reto.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    String admin = "admin";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static final int LEGAL_AGE = 18;

    public boolean isAdult(Date dateOfBirth) {
        LocalDate birthDate = LocalDate.ofInstant(dateOfBirth.toInstant(), ZoneId.systemDefault());
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears() >= LEGAL_AGE;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<List<Client>>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id)
                .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {

        if (validateEmail(client.getEmail()) && isAdult(client.getBirthdate())) {
            client.setUserCreate(admin);
            client.setUserEdit(admin);
            return new ResponseEntity<>(clientService.createClient(client), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        Optional<Client> clientData = clientService.getClientById(id);
        if (clientData.isPresent()) {
            Date editDate = Date.from(Instant.now());
            Client clientAux = clientData.get();
            clientAux.setFirstName(client.getFirstName());
            clientAux.setLastName(client.getLastName());
            clientAux.setEmail(client.getEmail());
            clientAux.setBirthdate(client.getBirthdate());
            clientAux.setIdentification(client.getIdentification());
            clientAux.setIdentificationType(client.getIdentificationType());
            clientAux.setEditDate(editDate);
            clientAux.setUserEdit(admin);
            if(isAdult(clientAux.getBirthdate())){
                return new ResponseEntity<>(clientService.createClient(clientAux), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(clientAux, HttpStatus.BAD_REQUEST);
            }
            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteClientById(@PathVariable("id") long id) {

        List<Account> accounts = accountService.getAllAccountByClientId(id);
        int verification = 0;
        for (Account acc : accounts) {
            if (acc.getAccount_state().equals(Account.State.CANCELED)) {
                verification++;
            }
        }
        if (verification < 1 && clientService.deleteClientById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
