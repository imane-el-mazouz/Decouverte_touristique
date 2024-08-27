package com.tourist.repository;

import com.tourist.enums.CategoryHotel;
import com.tourist.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> filterHotels(String location, Double price , CategoryHotel category);
}
