package com.tourist.repository;

import com.tourist.model.Event;
import com.tourist.model.Excursion;
import com.tourist.model.Reservation;
import com.tourist.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation , Long> {
    List<Reservation> findByEvent(Event event);

    List<Reservation> findByExcursion(Excursion excursion);

    List<Reservation> findByRoom(Room room);
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.client.id = :clientId")
    Optional<Reservation> findByRoomAndClient(@Param("roomId") Long roomId, @Param("clientId") Long clientId);


    List<Reservation> findAllByExcursionNotNull();

    List<Reservation> findAllByRoomNotNull();

    List<Reservation> findAllByEventNotNull();
}
