package com.tourist.service;

import com.tourist.dto.ReviewDTO;
import com.tourist.exception.ReviewNotFound;
import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.model.Review;
import com.tourist.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }


    public Review addReview(ReviewDTO reviewDTO, Client client, Reservation reservation) {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setDate(reviewDTO.getDate());
        review.setClient(client);
        review.setReservation(reservation);
        return reviewRepository.save(review);
    }


    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound("review not found " + id));
        reviewRepository.delete(review);
    }

    public Optional<Review> getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound("review not found " + id));
        return reviewRepository.findById(id);
    }


}
