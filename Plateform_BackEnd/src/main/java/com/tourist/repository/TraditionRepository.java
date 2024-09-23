package com.tourist.repository;

import com.tourist.model.Tradition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface TraditionRepository extends JpaRepository<Tradition , Long> {
    List<Tradition> findByCity(String city);
}
