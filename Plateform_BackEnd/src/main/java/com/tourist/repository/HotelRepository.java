package com.tourist.repository;

import com.tourist.enums.CategoryHotel;
import com.tourist.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE (:minRating IS NULL OR h.averageRating >= :minRating) AND (:maxRating IS NULL OR h.averageRating <= :maxRating)")
    List<Hotel> findAllByAverageRatingBetween(@Param("minRating") Long minRating, @Param("maxRating") Long maxRating);


    List<Hotel> findHotelByCategoryHotelOrLocation(CategoryHotel category, String location);
}
