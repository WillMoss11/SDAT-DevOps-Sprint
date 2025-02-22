package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String type;
    private String airlineName;
    private int numberOfPassengers;

    @ManyToMany(mappedBy = "aircraft")
    private List<Passenger> passengers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "aircraft_airport",
               joinColumns = @JoinColumn(name = "aircraft_id"),
               inverseJoinColumns = @JoinColumn(name = "airport_id"))
    private List<Airport> airports = new ArrayList<>();

    // Getters and setters (for all fields)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    public int getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }
}