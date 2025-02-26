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
@RequestMapping("/api/passengers")
public class PassengerController {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping
    public Page<Passenger> getAllPassengers(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return passengerRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
    }

    @PostMapping
    public Passenger createPassenger(@Valid @RequestBody Passenger passenger, @RequestParam Long cityId, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            throw new InvalidDataException("Invalid data provided", result);
        }
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City not found"));
        passenger.setCity(city);
        return passengerRepository.save(passenger);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @Valid @RequestBody Passenger passenger, @RequestParam Long cityId, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            throw new InvalidDataException("Invalid data provided", result);
        }
        Passenger existingPassenger = passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City not found"));
        existingPassenger.setFirstName(passenger.getFirstName());
        existingPassenger.setLastName(passenger.getLastName());
        existingPassenger.setPhoneNumber(passenger.getPhoneNumber());
        existingPassenger.setCity(city);
        return passengerRepository.save(existingPassenger);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerRepository.deleteById(id);
    }

    @PostMapping("/{passengerId}/addAircraft/{aircraftId}")
    public Passenger addAircraftToPassenger(@PathVariable Long passengerId, @PathVariable Long aircraftId) {
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        Aircraft aircraft = aircraftRepository.findById(aircraftId).orElseThrow(() -> new EntityNotFoundException("Aircraft not found"));

        passenger.getAircraft().add(aircraft);
        return passengerRepository.save(passenger);
    }

    @GetMapping("/{id}/aircraft")
    public List<Aircraft> getAircraftByPassengerId(@PathVariable Long id){
    return passengerRepository.findAircraftByPassengerId(id);
    }
    
}
