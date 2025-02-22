package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
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
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger, @RequestParam Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new EntityNotFoundException("City not found"));
        passenger.setCity(city);
        return passengerRepository.save(passenger);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger, @RequestParam Long cityId) {
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
