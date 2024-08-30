package com.tourist.controller;

import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findByEmail(String.valueOf(id));
        return ResponseEntity.ok(client);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")

    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client client = clientService.updateClient(id, updatedClient);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}/reservations")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")

    public ResponseEntity<List<Reservation>> getClientReservations(@PathVariable Long id) {
        List<Reservation> reservations = clientService.getClientReservations(id);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}/reviews")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")

    public ResponseEntity<List<Review>> getClientReviews(@PathVariable Long id) {
        List<Review> reviews = clientService.getClientReviews(id);
        return ResponseEntity.ok(reviews);
    }
}
