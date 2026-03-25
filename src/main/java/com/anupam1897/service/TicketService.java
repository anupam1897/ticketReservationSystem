package com.anupam1897.service;

import com.anupam1897.model.*;
import com.anupam1897.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class TicketService {

    @Autowired private RailwayDatabase       railwayDatabase;
    @Autowired private TicketRepository      ticketRepository;
    @Autowired private TrainRepository       trainRepository;
    @Autowired private PaymentRepository     paymentRepository;
    @Autowired private TicketClerkRepository clerkRepository;

    // ---- RESERVE (done by a clerk) ----
    public Ticket reserveTicket(Long passengerId, Long trainId, Long clerkId,
                                String from, String to,
                                String seatNumber, LocalDateTime journeyDate) {

        Passenger passenger = railwayDatabase.getPassengerById(passengerId);
        Train train         = railwayDatabase.getTrainById(trainId);
        TicketClerk clerk   = railwayDatabase.getClerkById(clerkId);

        if (!train.reserveSeat()) {
            throw new RuntimeException("No seats available on train " + train.getTrainNumber());
        }

        String ticketNo = "TKT-" + UUID.randomUUID().toString()
                                       .substring(0, 8).toUpperCase();

        Ticket ticket = new Ticket(ticketNo, from, to, train.getFarePerSeat(),
                                   seatNumber, journeyDate, passenger, train, clerk);

        trainRepository.save(train);
        return ticketRepository.save(ticket);
    }

    // ---- PURCHASE ----
    public Ticket purchaseTicket(Long ticketId, String paymentMethod) {

        Ticket ticket = railwayDatabase.getTicketById(ticketId);

        if (ticket.isCancelled())
            throw new RuntimeException("Cannot purchase a cancelled ticket");
        if (ticket.isPurchased())
            throw new RuntimeException("Ticket already purchased");

        ticket.purchase();
        ticketRepository.save(ticket);

        String txnId = "TXN-" + UUID.randomUUID().toString()
                                    .substring(0, 12).toUpperCase();

        Payment payment = new Payment(txnId, ticket.getFare(), paymentMethod, ticket);
        payment.complete();
        paymentRepository.save(payment);

        return ticket;
    }

    // ---- CANCEL ----
    public Ticket cancelTicket(Long ticketId) {

        Ticket ticket = railwayDatabase.getTicketById(ticketId);

        if (ticket.isCancelled())
            throw new RuntimeException("Ticket is already cancelled");

        ticket.cancel();
        ticket.getTrain().releaseSeat();
        trainRepository.save(ticket.getTrain());
        ticketRepository.save(ticket);

        // refund if already paid
        if (ticket.getPayment() != null) {
            Payment payment = ticket.getPayment();
            payment.refund();
            paymentRepository.save(payment);
        }

        return ticket;
    }

    // ---- QUERIES ----
    public Ticket getTicketById(Long id) {
        return railwayDatabase.getTicketById(id);
    }
}