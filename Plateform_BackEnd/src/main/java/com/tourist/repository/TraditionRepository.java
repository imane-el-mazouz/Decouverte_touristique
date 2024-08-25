package com.tourist.repository;

import com.tourist.model.Tradition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface TraditionRepository extends JpaRepository<Tradition , Long> {
}
