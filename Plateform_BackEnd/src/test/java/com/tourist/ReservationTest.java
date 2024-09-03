//package com.tourist;
//
//import com.tourist.enums.Status;
//import com.tourist.exception.EventFullyBookedException;
//import com.tourist.model.*;
//import com.tourist.repository.*;
//import com.tourist.service.ReservationService;
//import jakarta.persistence.EntityNotFoundException;
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
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class ReservationTest {
//        @Mock
//        private ReservationRepository reservationRepository;
//
//        @Mock
//        private EventRepository eventRepository;
//
//        @Mock
//        private ExcursionRepository excursionRepository;
//
//        @Mock
//        private HotelRepository hotelRepository;
//
//        @Mock
//        private RoomRepository roomRepository;
//
//        @Mock
//        private ClientRepository clientRepository;
//
//        @InjectMocks
//        private ReservationService reservationService;
//
//        @BeforeEach
//        void setUp() {
//            MockitoAnnotations.openMocks(this);
//        }
//
//        @Test
//        void testReserveEventSuccess() {
//            Long clientId = 1L;
//            Long eventId = 1L;
//            Long numberOfPerson = 2L;
//            LocalDate dateTime = LocalDate.now();
//
//            Client client = new Client();
//            Event event = new Event();
//            event.setCapacity(10);
//
//            when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//            when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
//            when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//
//            Reservation reservation = reservationService.reserveEvent(clientId, eventId, numberOfPerson, dateTime);
//
//            assertNotNull(reservation);
//            assertEquals(Status.Confirmed, reservation.getStatus());
//            assertEquals(client, reservation.getClient());
//            assertEquals(event, reservation.getEvent());
//            verify(eventRepository).findById(eventId);
//            verify(reservationRepository).save(any(Reservation.class));
//        }
//
//        @Test
//        void testReserveEventFullyBooked() {
//            Long clientId = 1L;
//            Long eventId = 1L;
//            Long numberOfPerson = 2L;
//            LocalDate dateTime = LocalDate.now();
//
//            Client client = new Client();
//            Event event = new Event();
//            event.setCapacity(0);
//
//            when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//            when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
//
//            assertThrows(EventFullyBookedException.class, () -> reservationService.reserveEvent(clientId, eventId, numberOfPerson, dateTime));
//        }
//
//        @Test
//        void testReserveExcursionSuccess() {
//            Long clientId = 1L;
//            Long excursionId = 1L;
//            Long numberOfPerson = 2L;
//            LocalDate dateTime = LocalDate.now();
//
//            Client client = new Client();
//            Excursion excursion = new Excursion();
//            excursion.setCapacity(10);
//
//            when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//            when(excursionRepository.findById(excursionId)).thenReturn(Optional.of(excursion));
//            when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//
//            Reservation reservation = reservationService.reserveExcursion(clientId, excursionId, numberOfPerson, dateTime);
//
//            assertNotNull(reservation);
//            assertEquals(Status.Confirmed, reservation.getStatus());
//            assertEquals(client, reservation.getClient());
//            assertEquals(excursion, reservation.getExcursion());
//            verify(excursionRepository).findById(excursionId);
//            verify(reservationRepository).save(any(Reservation.class));
//        }
//
//        @Test
//        void testReserveHotelSuccess() {
//            Long clientId = 1L;
//            Long roomId = 1L;
//            Long numberOfPerson = 2L;
//            LocalDate checkInDate = LocalDate.now();
//            LocalDate checkOutDate = LocalDate.now().plusDays(1);
//
//            Client client = new Client();
//            Room room = new Room();
//
//            when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
//            when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
//            when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//
//            Reservation reservation = reservationService.reserveHotel(clientId, roomId, numberOfPerson, checkInDate, checkOutDate);
//
//            assertNotNull(reservation);
//            assertEquals(Status.Confirmed, reservation.getStatus());
//            assertEquals(client, reservation.getClient());
//            assertEquals(room, reservation.getRoom());
//            assertEquals(Date.valueOf(checkInDate), reservation.getCheckInDate());
//            assertEquals(Date.valueOf(checkOutDate), reservation.getCheckOutDate());
//            verify(roomRepository).findById(roomId);
//            verify(reservationRepository).save(any(Reservation.class));
//        }
//
//        @Test
//        void testListReservationsForEvent() {
//            Long eventId = 1L;
//            Event event = new Event();
//            List<Reservation> reservations = new ArrayList<>();
//            reservations.add(new Reservation());
//
//            when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
//            when(reservationRepository.findByEvent(event)).thenReturn(reservations);
//
//            List<Reservation> result = reservationService.listReservationsForEvent(eventId);
//
//            assertNotNull(result);
//            assertFalse(result.isEmpty());
//            verify(eventRepository).findById(eventId);
//            verify(reservationRepository).findByEvent(event);
//        }
//
//        @Test
//        void testListReservationsForExcursion() {
//            Long excursionId = 1L;
//            Excursion excursion = new Excursion();
//            List<Reservation> reservations = new ArrayList<>();
//            reservations.add(new Reservation());
//
//            when(excursionRepository.findById(excursionId)).thenReturn(Optional.of(excursion));
//            when(reservationRepository.findByExcursion(excursion)).thenReturn(reservations);
//
//            List<Reservation> result = reservationService.listReservationsForExcursion(excursionId);
//
//            assertNotNull(result);
//            assertFalse(result.isEmpty());
//            verify(excursionRepository).findById(excursionId);
//            verify(reservationRepository).findByExcursion(excursion);
//        }
//
//        @Test
//        void testListReservationsForHotel() {
//            Long roomId = 1L;
//            Room room = new Room();
//            List<Reservation> reservations = new ArrayList<>();
//            reservations.add(new Reservation());
//
//            when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
//            when(reservationRepository.findByRoom(room)).thenReturn(reservations);
//
//            List<Reservation> result = reservationService.listReservationsForHotel(roomId);
//
//            assertNotNull(result);
//            assertFalse(result.isEmpty());
//            verify(roomRepository).findById(roomId);
//            verify(reservationRepository).findByRoom(room);
//        }
//
//        @Test
//        void testDeleteReservation() {
//            Long reservationId = 1L;
//            Reservation reservation = new Reservation();
//
//            when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
//
//            reservationService.deleteReservation(reservationId);
//
//            verify(reservationRepository).delete(reservation);
//        }
//
//        @Test
//        void testDeleteReservationNotFound() {
//            Long reservationId = 1L;
//
//            when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
//
//            assertThrows(EntityNotFoundException.class, () -> reservationService.deleteReservation(reservationId));
//        }
//
//    }
//
