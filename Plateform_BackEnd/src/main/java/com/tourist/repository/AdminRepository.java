package com.tourist.repository;


import com.tourist.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//@Repository
public interface AdminRepository extends JpaRepository <Admin , Long> {

    @Query("SELECT COUNT(d.totalClients) FROM DashAdmin d")
    Long countTotalClients();

    @Query("SELECT COUNT(d.totalEvents) FROM DashAdmin d")
    Long countTotalEvents();

    @Query("SELECT COUNT(d.totalExcursions) FROM DashAdmin d")
    Long countTotalExcursions();

    @Query("SELECT COUNT(d.totalHotels) FROM DashAdmin d")
    Long countTotalHotels();

    @Query("SELECT COUNT(d.totalRoomsPerHotel) FROM DashAdmin d")
    Long countTotalRoomsPerHotel();

    @Query("SELECT COUNT(d.totalReservations) FROM DashAdmin d")
    Long countTotalReservations();
}
