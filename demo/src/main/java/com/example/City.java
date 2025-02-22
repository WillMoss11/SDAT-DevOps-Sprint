package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String state;
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
