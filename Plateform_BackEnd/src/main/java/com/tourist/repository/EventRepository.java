package com.tourist.repository;

import com.tourist.enums.CategoryEvent;
import com.tourist.model.Event;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository <Event , Long> {

    List<Event> findEventByCategoryOrLocationOrDateTime(CategoryEvent category, String location, LocalDateTime dateTime);

    List<Event> findAllByPriceBetweenAndRatingBetweenAndDistanceLessThan(
            Double minPrice, Double maxPrice,
            Integer minRating, Integer maxRating,
            Double maxDistance
    );
}
