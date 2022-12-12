package com.reto.backend.service;

import com.reto.backend.entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    public Client createClient(Client client);
    public List<Client> getAllClients();
    public Optional<Client> getClientById(long id);
    public boolean deleteClientById(long id);
}
