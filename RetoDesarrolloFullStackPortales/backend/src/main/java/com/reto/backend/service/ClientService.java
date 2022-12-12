package com.reto.backend.service;

import com.reto.backend.entity.Client;
import com.reto.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {

        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public boolean deleteClientById(long id) {
        return clientRepository.findById(id).map(client -> {
            clientRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
