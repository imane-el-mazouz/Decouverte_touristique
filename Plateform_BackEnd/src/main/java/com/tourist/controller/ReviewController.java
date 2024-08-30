package com.tourist.controller;


import com.tourist.dto.ReviewDTO;
import com.tourist.exception.ReservationNotFoundException;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.service.ClientService;
import com.tourist.service.ReservationService;
import com.tourist.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("*")
public class ReviewController {
    private final ReviewService reviewService;

    private final ClientService clientService;

    private final ReservationService reservationService ;

    public ReviewController(ReviewService reviewService, ClientService clientService, ReservationService reservationService) {
        this.reviewService = reviewService;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')" )
    public ResponseEntity<List<Review>> getAllReviews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasRole('Client')")
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO, @RequestParam Long reservationId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Client client = clientService.findByEmail(email);
            Reservation reservation = (Reservation) reservationService.getReservationById(reservationId)
                    .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
            Review savedReview = reviewService.addReview(reviewDTO, client, reservation);
            return ResponseEntity.ok(savedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id){
         reviewService.deleteReview(id);
         return ResponseEntity.noContent().build();
    }



}
