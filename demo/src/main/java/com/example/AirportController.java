package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public Page<Airport> getAllAirports(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return airportRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Airport getAirportById(@PathVariable Long id) {
        return airportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Airport not found"));
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport, @RequestParam Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City not found"));
        airport.setCity(city);
        return airportRepository.save(airport);
    }

    @PutMapping("/{id}")
    public Airport updateAirport(@PathVariable Long id, @RequestBody Airport airport, @RequestParam Long cityId) {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Airport not found"));
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City not found"));
        existingAirport.setName(airport.getName());
        existingAirport.setCode(airport.getCode());
        existingAirport.setCity(city);
        return airportRepository.save(existingAirport);
    }

    @DeleteMapping("/{id}")
    public void deleteAirport(@PathVariable Long id) {
        airportRepository.deleteById(id);
    }

    @GetMapping("/passengers/{id}")
    public List<Airport> getAirportsByPassengerId(@PathVariable Long id){
    return airportRepository.findAirportsByPassengerId(id);
    }
}
