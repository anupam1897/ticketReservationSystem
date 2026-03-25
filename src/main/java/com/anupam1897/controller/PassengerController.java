package com.anupam1897.controller;

import com.anupam1897.model.*;
import com.anupam1897.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Passenger> register(@RequestBody Passenger passenger) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(passengerService.register(passenger));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<Ticket>> viewSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getSchedule(id));
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> viewAllTickets(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getAllTickets(id));
    }
}