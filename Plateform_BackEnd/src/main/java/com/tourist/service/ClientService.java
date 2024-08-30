package com.tourist.service;

import com.tourist.exception.ClientNotFoundException;
import com.tourist.exception.ReviewNotFound;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        client.setName(updatedClient.getName());
        client.setEmail(updatedClient.getEmail());
        return clientRepository.save(client);
    }

    public List<Reservation> getClientReservations(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        return client.getReservations();
    }

    public List<Review> getClientReviews(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ReviewNotFound("Client not found with id: " + clientId));
        return client.getReviews();
    }
}
