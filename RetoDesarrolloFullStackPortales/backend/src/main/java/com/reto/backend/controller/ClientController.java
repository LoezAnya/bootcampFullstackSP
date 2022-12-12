package com.reto.backend.controller;

import com.reto.backend.entity.Client;
import com.reto.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<List<Client>>(clientService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id)
                .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {

        return new ResponseEntity<>(clientService.createClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
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

            return new ResponseEntity<>(clientService.createClient(clientAux), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity deleteClientById(@PathVariable("id") long id) {
        if (clientService.deleteClientById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
