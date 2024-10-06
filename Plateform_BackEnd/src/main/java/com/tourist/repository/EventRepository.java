package com.tourist.repository;

import com.tourist.enums.CategoryEvent;
import com.tourist.model.Event;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findEventByCategoryOrLocationOrDate(
            CategoryEvent category,
            String location,
            LocalDate date);


    List<Event> findAllByPriceBetweenAndRatingBetweenAndDistanceLessThan(
            Double minPrice, Double maxPrice,
            Integer minRating, Integer maxRating,
            Double maxDistance
    );
}
