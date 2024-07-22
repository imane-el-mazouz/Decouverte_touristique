package com.tourist.repository;

import com.tourist.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    void deleteCardByAccountIdA(Long idA);
}
