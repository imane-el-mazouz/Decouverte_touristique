package com.tourist.controller;


import com.tourist.dto.ReviewDTO;
import com.tourist.exception.RatingException;
import com.tourist.exception.ReservationNotFoundException;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.service.ClientService;
import com.tourist.service.ReservationService;
import com.tourist.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//@PreAuthorize("hasRole('Client')")
//@PostMapping("/reservation/{reservationId}")
//public ResponseEntity<Review> addReview(@PathVariable Long reservationId, @RequestBody ReviewDTO reviewDTO) {
//    if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
//        throw new RatingException("Rating must be between 1 and 5");
//    }
//
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String email = authentication.getName();
//    Client client = clientService.findByEmail(email);
//
//    if (client == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//
//    Review review = new Review();
//    review.setRating(reviewDTO.getRating());
//    review.setComment(reviewDTO.getComment());
//    review.setClient(client);
//
//    Review createdReview = reviewService.addReview(reservationId, review);
//    return ResponseEntity.ok(createdReview);
//}
@PreAuthorize("hasRole('Client')")
@PostMapping("/room/{roomId}/add-review")
public ResponseEntity<Review> addReview(
        @PathVariable Long roomId,
        @RequestBody ReviewDTO reviewDTO) {

    if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
        throw new RatingException("Rating must be between 1 and 5");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Client client = clientService.findByEmail(email);

    if (client == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Reservation> reservationOpt = reviewService.findByRoomAndClient(roomId, client.getId());
    if (!reservationOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Review());
    }

    Reservation reservation = reservationOpt.get();
    Review review = new Review();
    review.setRating(reviewDTO.getRating());
    review.setComment(reviewDTO.getComment());
    review.setClient(client);
    review.setReservation(reservation);

    Review createdReview = reviewService.addReview(reservation.getId(), review);
    return ResponseEntity.ok(createdReview);
}


    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id){
         reviewService.deleteReview(id);
         return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<Review> getAReviewById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Client client = clientService.findByEmail(email);
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }


}
