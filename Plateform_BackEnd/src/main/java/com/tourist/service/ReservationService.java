package com.tourist.service;

import com.tourist.enums.Status;
import com.tourist.exception.ClientNotFoundException;
import com.tourist.exception.EventFullyBookedException;
import com.tourist.exception.EventNotFoundException;
import com.tourist.model.*;
import com.tourist.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Reservation reserveEvent(Long clientId, Long eventId, Long numberOfPerson ,LocalDate dateTime) throws EventFullyBookedException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setEvent(event);
        reservation.setNumberOfPerson(numberOfPerson);
        reservation.setDateTime(dateTime);

        reservation.setStatus(Status.Confirmed);
        if(event.getCapacity()<=0){
            throw new EventFullyBookedException("Event is fully booked");
        }
        event.setCapacity(event.getCapacity()-1);

        return reservationRepository.save(reservation);
    }

    public Reservation reserveExcursion(Long clientId, Long excursionId, Long numberOfPerson ,LocalDate dateTime) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        Excursion excursion = excursionRepository.findById(excursionId)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + excursionId));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setExcursion(excursion);
        reservation.setNumberOfPerson(numberOfPerson);
        reservation.setDateTime(dateTime);
        reservation.setStatus(Status.Confirmed);
        if(excursion.getCapacity()<=0){
            throw new EventFullyBookedException("excursion is fully booked");
        }
        excursion.setCapacity(excursion.getCapacity()-1);

        return reservationRepository.save(reservation);
    }

    public Reservation reserveHotel(Long clientId, Long roomId,Long numberOfPerson , LocalDate checkInDate, LocalDate checkOutDate ) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EventNotFoundException("Hotel room not found with id: " + roomId));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setNumberOfPerson(numberOfPerson);
        reservation.setCheckInDate(Date.valueOf(checkInDate));
        reservation.setCheckOutDate(Date.valueOf(checkOutDate));
        reservation.setReservedRoom(true);
        reservation.setStatus(Status.Confirmed);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> listReservationsForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));

        return reservationRepository.findByEvent(event);
    }
    public List<Reservation> listReservationsForExcursion(Long excursionId) {
        Excursion excursion = excursionRepository.findById(excursionId)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + excursionId));

        return reservationRepository.findByExcursion(excursion);
    }

    public List<Reservation> listReservationsForHotel(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel room not found with id: " + roomId));

        return reservationRepository.findByRoom(room);
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));

        reservationRepository.delete(reservation);
    }


    public List<Reservation> listAllReservationsForExcursions() {
        return reservationRepository.findAllByExcursionNotNull();
    }

    public List<Reservation> listAllReservationsForHotels() {
        return reservationRepository.findAllByRoomNotNull();
    }

    public List<Reservation> listAllReservationsForEvents() {
        return reservationRepository.findAllByEventNotNull();
    }

}
