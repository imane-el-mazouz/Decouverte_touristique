package com.tourist.service;

import com.tourist.model.Reservation;
import com.tourist.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<Reservation> getReservationById(Long id){
        return reservationRepository.findById(id);
    }
}
