package com.anupam1897.controller;
import com.anupam1897.model.Ticket;
import com.anupam1897.model.TicketClerk;
import com.anupam1897.service.RailwayDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clerks")
public class ClerkController {

    @Autowired
    private RailwayDatabase railwayDatabase;

    // POST /api/clerks - Add new clerk
    @PostMapping
    public ResponseEntity<TicketClerk> addClerk(@RequestBody TicketClerk clerk) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(railwayDatabase.addClerk(clerk));
    }

    // GET /api/clerks - Get all clerks
    @GetMapping
    public ResponseEntity<List<TicketClerk>> getAllClerks() {
        return ResponseEntity.ok(railwayDatabase.getAllClerks());
    }

    // GET /api/clerks/{id} - Get clerk by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketClerk> getClerk(@PathVariable Long id) {
        return ResponseEntity.ok(railwayDatabase.getClerkById(id));
    }

    // GET /api/clerks/{id}/tickets - Get tickets processed by clerk
    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<Ticket>> getClerkTickets(@PathVariable Long id) {
        return ResponseEntity.ok(railwayDatabase.getTicketsByClerk(id));
    }
}