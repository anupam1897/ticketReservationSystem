package com.anupam1897.controller;

import com.anupam1897.model.Payment;
import com.anupam1897.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Payment> getPaymentByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(paymentService.getPaymentByTicketId(ticketId));
    }
}