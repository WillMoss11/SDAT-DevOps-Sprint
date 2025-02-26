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
@RequestMapping("/api/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public Page<City> getAllCities(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cityRepository.findAll(pageable);
    }
    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found"));
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityRepository.save(city);
    }

    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City city) {
        City existingCity = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found"));
        existingCity.setName(city.getName());
        existingCity.setState(city.getState());
        existingCity.setPopulation(city.getPopulation());
        return cityRepository.save(existingCity);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityRepository.deleteById(id);
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByCityId(@PathVariable Long id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found"));
        return city.getAirports();
    }
}