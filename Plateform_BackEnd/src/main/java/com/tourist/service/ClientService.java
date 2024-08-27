package com.tourist.service;

import com.tourist.model.Client;
import com.tourist.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository ;
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
