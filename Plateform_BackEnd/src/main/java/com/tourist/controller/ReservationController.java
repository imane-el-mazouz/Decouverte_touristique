package com.tourist.controller;

import com.tourist.model.Reservation;
import com.tourist.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Reserve an event
    @PostMapping("/reserve-event")
    public ResponseEntity<Reservation> reserveEvent(
            @RequestParam Long clientId,
            @RequestParam Long eventId,
            @RequestParam LocalDate dateTime) {
        Reservation reservation = reservationService.reserveEvent(clientId, eventId, dateTime);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // Reserve an excursion
    @PostMapping("/reserve-excursion")
    public ResponseEntity<Reservation> reserveExcursion(
            @RequestParam Long clientId,
            @RequestParam Long excursionId,
            @RequestParam LocalDate dateTime) {
        Reservation reservation = reservationService.reserveExcursion(clientId, excursionId, dateTime);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // Reserve a hotel room
    @PostMapping("/reserve-hotel")
    public ResponseEntity<Reservation> reserveHotel(
            @RequestParam Long clientId,
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {
        Reservation reservation = reservationService.reserveHotel(clientId, roomId, checkInDate, checkOutDate);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // List all reservations for a specific event
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Reservation>> listReservationsForEvent(@PathVariable Long eventId) {
        List<Reservation> reservations = reservationService.listReservationsForEvent(eventId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // List all reservations for a specific excursion
    @GetMapping("/excursion/{excursionId}")
    public ResponseEntity<List<Reservation>> listReservationsForExcursion(@PathVariable Long excursionId) {
        List<Reservation> reservations = reservationService.listReservationsForExcursion(excursionId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // List all reservations for a specific hotel room
    @GetMapping("/hotel/{roomId}")
    public ResponseEntity<List<Reservation>> listReservationsForHotel(@PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.listReservationsForHotel(roomId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Delete a reservation by ID
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
