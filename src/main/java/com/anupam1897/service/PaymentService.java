package com.anupam1897.service;

import com.anupam1897.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private RailwayDatabase railwayDatabase;

    public List<Payment> getAllPayments() {
        return railwayDatabase.getAllPayments();
    }

    public Payment getPaymentByTicketId(Long ticketId) {
        return railwayDatabase.getPaymentByTicketId(ticketId);
    }
}