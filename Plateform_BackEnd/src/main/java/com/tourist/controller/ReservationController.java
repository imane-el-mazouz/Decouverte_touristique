package com.tourist.controller;

import com.tourist.model.Client;
import com.tourist.model.Reservation;
import com.tourist.service.ClientService;
import com.tourist.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin("*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService ;

    @PostMapping("/event")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<Reservation> reserveEvent(
            @RequestParam Long eventId,
            @RequestParam LocalDate dateTime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Client client = clientService.findByEmail(email);
        Reservation reservation = reservationService.reserveEvent(client.getId(), eventId, dateTime);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PostMapping("/excursion")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<Reservation> reserveExcursion(
            @RequestParam Long excursionId,
            @RequestParam LocalDate dateTime) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Client client = clientService.findByEmail(email);
        Reservation reservation = reservationService.reserveExcursion(client.getId(), excursionId, dateTime);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PostMapping("/hotel")
    @PreAuthorize("hasRole('Client')")
    public ResponseEntity<Reservation> reserveHotel(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Client client = clientService.findByEmail(email);
        Reservation reservation = reservationService.reserveHotel(client.getId(), roomId, checkInDate, checkOutDate);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }


    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasRole('Admin')")

    public ResponseEntity<List<Reservation>> listReservationsForEvent(@PathVariable Long eventId) {
        List<Reservation> reservations = reservationService.listReservationsForEvent(eventId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/excursion/{excursionId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Reservation>> listReservationsForExcursion(@PathVariable Long excursionId) {
        List<Reservation> reservations = reservationService.listReservationsForExcursion(excursionId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/hotel/{roomId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Reservation>> listReservationsForHotel(@PathVariable Long roomId) {
        List<Reservation> reservations = reservationService.listReservationsForHotel(roomId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
