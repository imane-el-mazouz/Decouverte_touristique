package com.tourist.repository;

import com.tourist.enums.CategoryEvent;
import com.tourist.model.Event;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query("SELECT e FROM Event e WHERE " +
            "(:minPrice IS NULL OR e.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR e.price <= :maxPrice) AND " +
            "(:minRating IS NULL OR e.rating >= :minRating) AND " +
            "(:maxRating IS NULL OR e.rating <= :maxRating) AND " +
            "(:maxDistance IS NULL OR e.distance <= :maxDistance)")
    List<Event> findEventsByCriteria(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating,
            @Param("maxDistance") Double maxDistance
    );
}