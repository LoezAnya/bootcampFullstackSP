package com.reto.backend.controller;

import com.reto.backend.entity.Client;
import com.reto.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

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
    public ResponseEntity<Client> createClient(@RequestBody Client client){

        return new ResponseEntity<>(clientService.createClient(client),HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteClientById(@PathVariable("id") long id){
        if (clientService.deleteClientById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
