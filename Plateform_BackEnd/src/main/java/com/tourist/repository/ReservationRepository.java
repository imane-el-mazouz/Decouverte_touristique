package com.tourist.repository;

import com.tourist.model.Event;
import com.tourist.model.Excursion;
import com.tourist.model.Reservation;
import com.tourist.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ReservationRepository extends JpaRepository<Reservation , Long> {
    List<Reservation> findByEvent(Event event);

    List<Reservation> findByExcursion(Excursion excursion);

    List<Reservation> findByRoom(Room room);
}
