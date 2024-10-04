package com.tourist.service;

import com.tourist.dto.ReviewDTO;
import com.tourist.exception.RatingException;
import com.tourist.exception.ReservationNotFoundException;
import com.tourist.exception.ReviewNotFound;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.repository.ReservationRepository;
import com.tourist.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private ReservationRepository reservationRepository;




    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }


//    public Review addReview(ReviewDTO reviewDTO, Client client, Reservation reservation) {
//        Review review = new Review();
//        review.setRating(reviewDTO.getRating());
//        review.setComment(reviewDTO.getComment());
//        review.setDate(reviewDTO.getDate());
//        review.setClient(client);
//        review.setReservation(reservation);
//        return reviewRepository.save(review);
//    }

    public Optional<Reservation> findByRoomAndClient(Long roomId, Long clientId) {
        return reservationRepository.findByRoomAndClient(roomId, clientId);
    }

    @Transactional
    public Review addReview(Long reservationId, Review review) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id " + reservationId));
        review.setReservation(reservation);
        review.setDate(LocalDate.now());
        return reviewRepository.save(review);
    }




    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound("review not found " + id));
        reviewRepository.delete(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound("Review not found: " + id));
    }



}
