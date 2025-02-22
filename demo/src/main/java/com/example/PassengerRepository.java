package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query("SELECT a FROM Aircraft a JOIN a.passengers p WHERE p.id = :passengerId")
    List<Aircraft> findAircraftByPassengerId(@Param("passengerId") Long passengerId);
}
