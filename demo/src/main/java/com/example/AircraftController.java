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
@RequestMapping("/api/aircrafts")
public class AircraftController {
    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AirportRepository airportRepository;

    @GetMapping
    public Page<Aircraft> getAllAircraft(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return aircraftRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Aircraft getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        Aircraft existingAircraft = aircraftRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
        existingAircraft.setType(aircraft.getType());
        existingAircraft.setAirlineName(aircraft.getAirlineName());
        existingAircraft.setNumberOfPassengers(aircraft.getNumberOfPassengers());
        return aircraftRepository.save(existingAircraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable Long id) {
        aircraftRepository.deleteById(id);
    }

    @PostMapping("/{aircraftId}/addAirport/{airportId}")
    public Aircraft addAirportToAircraft(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
        Airport airport = airportRepository.findById(airportId).orElseThrow(() -> new EntityNotFoundException("Airport not found"));

        aircraft.getAirports().add(airport);
        return aircraftRepository.save(aircraft);
    }

    @DeleteMapping("/{aircraftId}/removeAirport/{airportId}")
    public Aircraft removeAirportFromAircraft(@PathVariable Long aircraftId, @PathVariable Long airportId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
        Airport airport = airportRepository.findById(airportId).orElseThrow(() -> new EntityNotFoundException("Airport not found"));

        aircraft.getAirports().remove(airport);
        airport.getAircraft().remove(aircraft);
        return aircraftRepository.save(aircraft);
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByAircraftId(@PathVariable Long id) {
        Aircraft aircraft = aircraftRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
        return aircraft.getAirports();
    }



    @GetMapping("/{id}/passengers")
    public List<Passenger> getPassengersByAircraftId(@PathVariable Long id) {
        Aircraft aircraft = aircraftRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));
        return aircraft.getPassengers();
    }
}
