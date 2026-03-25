package com.anupam1897.controller;

import com.anupam1897.model.Ticket;
import com.anupam1897.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // POST /api/tickets/reserve
    @PostMapping("/reserve")
    public ResponseEntity<Ticket> reserve(@RequestBody Map<String, Object> body) {
        Long   passengerId = Long.valueOf(body.get("passengerId").toString());
        Long   trainId     = Long.valueOf(body.get("trainId").toString());
        Long   clerkId     = Long.valueOf(body.get("clerkId").toString());
        String from        = body.get("from").toString();
        String to          = body.get("to").toString();
        String seatNumber  = body.get("seatNumber").toString();
        LocalDateTime journeyDate = LocalDateTime.parse(body.get("journeyDate").toString());

        Ticket ticket = ticketService.reserveTicket(
            passengerId, trainId, clerkId, from, to, seatNumber, journeyDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    // POST /api/tickets/{id}/purchase
    @PostMapping("/{id}/purchase")
    public ResponseEntity<Ticket> purchase(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {
        String method = body.getOrDefault("paymentMethod", "CARD");
        return ResponseEntity.ok(ticketService.purchaseTicket(id, method));
    }

    // DELETE /api/tickets/{id}/cancel
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Ticket> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.cancelTicket(id));
    }

    // GET /api/tickets/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
}