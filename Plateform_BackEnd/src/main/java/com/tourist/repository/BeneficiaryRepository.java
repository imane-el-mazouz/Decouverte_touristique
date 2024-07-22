package com.tourist.repository;

import com.tourist.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    @Query("UPDATE Beneficiary b SET b.sold = :sold WHERE b.idB = :idB")
    void updateBeneficiarySold(Long idB, Double sold);

    Optional<Beneficiary> findByRib(Long rib);
}
