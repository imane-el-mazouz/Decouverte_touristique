//package com.tourist;
//
//import com.tourist.dto.ReviewDTO;
//import com.tourist.exception.RatingException;
//import com.tourist.exception.ReservationNotFoundException;
//import com.tourist.exception.ReviewNotFound;
//import com.tourist.model.Client;
//import com.tourist.model.Reservation;
//import com.tourist.model.Review;
//import com.tourist.repository.ReservationRepository;
//import com.tourist.repository.ReviewRepository;
//import com.tourist.service.ReviewService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class ReviewTest {
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private ReservationRepository reservationRepository;
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllReviews() {
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review());
//        reviews.add(new Review());
//
//        when(reviewRepository.findAll()).thenReturn(reviews);
//
//        List<Review> result = reviewService.getAllReviews();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(reviewRepository).findAll();
//    }
//
//    @Test
//    void testAddReviewSuccess() {
//        Long reservationId = 1L;
//        Reservation reservation = new Reservation();
//        Review review = new Review();
//        review.setRating(5L);
//        review.setComment("Great!");
//        review.setDate(Date.valueOf(LocalDate.now()).toLocalDate());
//
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
//        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//
//        Review result = reviewService.addReview(reservationId, review);
//
//        assertNotNull(result);
//        assertEquals(reservation, result.getReservation());
//        verify(reservationRepository).findById(reservationId);
//        verify(reviewRepository).save(any(Review.class));
//    }
//
//    @Test
//    void testAddReviewReservationNotFound() {
//        Long reservationId = 1L;
//        Review review = new Review();
//
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
//
//        assertThrows(ReservationNotFoundException.class, () -> reviewService.addReview(reservationId, review));
//    }
//
//    @Test
//    void testDeleteReviewSuccess() {
//        Long reviewId = 1L;
//        Review review = new Review();
//
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
//
//        reviewService.deleteReview(reviewId);
//
//        verify(reviewRepository).delete(review);
//    }
//
//    @Test
//    void testDeleteReviewNotFound() {
//        Long reviewId = 1L;
//
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
//
//        assertThrows(ReviewNotFound.class, () -> reviewService.deleteReview(reviewId));
//    }
//
//    @Test
//    void testGetReviewByIdSuccess() {
//        Long reviewId = 1L;
//        Review review = new Review();
//
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
//
//        Review result = reviewService.getReviewById(reviewId);
//
//        assertNotNull(result);
//        assertEquals(review, result);
//        verify(reviewRepository).findById(reviewId);
//    }
//
//    @Test
//    void testGetReviewByIdNotFound() {
//        Long reviewId = 1L;
//
//        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
//
//        assertThrows(ReviewNotFound.class, () -> reviewService.getReviewById(reviewId));
//    }
//}
