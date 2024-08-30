package com.tourist.repository;

import com.tourist.model.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    List<Excursion> findByDateTime(LocalDateTime dateTime);

    List<Excursion> findByLocation(String location);

    List<Excursion> findByDateTimeAndLocation(LocalDateTime dateTime, String location);

    List<Excursion> findByRatingBetween(Long minRating, Long maxRating);
}
