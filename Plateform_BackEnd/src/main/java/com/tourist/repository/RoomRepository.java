package com.tourist.repository;


import com.tourist.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room , Long> {
    List<Room> findByHotel_IdHotel(Long IdHotel);
}
