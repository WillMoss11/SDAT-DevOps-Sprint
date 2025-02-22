package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT DISTINCT ap FROM Airport ap JOIN ap.aircraft ac JOIN ac.passengers p WHERE p.id = :passengerId")
    List<Airport> findAirportsByPassengerId(@Param("passengerId") Long passengerId);
    
}
