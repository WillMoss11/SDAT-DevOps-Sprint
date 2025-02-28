package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "City name cannot be null") // Add validation for name
    @Size(min = 1, max = 100, message = "City name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "State cannot be null") // Add validation for state
    @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
    private String state;

    @Min(value = 0, message = "Population cannot be negative")
    @Max(value = 50_000_000, message = "Population must be realistic (less than 50 million)")
    private int population;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Airport> airports = new ArrayList<>();

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Passenger> passengers = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }
    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}
