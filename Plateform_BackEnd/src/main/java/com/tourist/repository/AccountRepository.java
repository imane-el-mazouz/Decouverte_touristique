package com.tourist.repository;

import com.tourist.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.idU = :idU")
    List<Account> findByUserId(@Param("idU") Long idU);

    @Query("SELECT a.sold FROM Account a WHERE a.idA = :idA")
    Double findAccountSoldById(@Param("idA") Long idA);

    Optional<Account> findByRib(Long rib);
}
