package com.anupam1897.service;

import com.anupam1897.model.*;
import com.anupam1897.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RailwayDatabase {

    @Autowired private TrainRepository       trainRepository;
    @Autowired private PassengerRepository   passengerRepository;
    @Autowired private TicketRepository      ticketRepository;
    @Autowired private PaymentRepository     paymentRepository;
    @Autowired private TicketClerkRepository clerkRepository;

    // ========== TRAIN OPERATIONS ==========
    public Train addTrain(Train train) {
        if (trainRepository.findByTrainNumber(train.getTrainNumber()).isPresent()) {
            throw new RuntimeException("Train " + train.getTrainNumber() + " already exists");
        }
        train.setAvailableSeats(train.getTotalSeats());
        return trainRepository.save(train);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Train not found: " + id));
    }

    public Train getTrainByNumber(String number) {
        return trainRepository.findByTrainNumber(number)
            .orElseThrow(() -> new RuntimeException("Train not found: " + number));
    }

    public List<Train> searchTrains(String source, String destination) {
        if (source != null && destination != null)
            return trainRepository.findBySourceAndDestination(source, destination);
        if (source != null)
            return trainRepository.findBySource(source);
        if (destination != null)
            return trainRepository.findByDestination(destination);
        return trainRepository.findAll();
    }

    public void deleteTrain(Long id) {
        Train train = getTrainById(id);
        trainRepository.delete(train);
    }

    // ========== PASSENGER OPERATIONS ==========
    public Passenger addPassenger(Passenger passenger) {
        if (passenger.getEmail() != null &&
            passengerRepository.findByEmail(passenger.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        return passengerRepository.save(passenger);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Passenger not found: " + id));
    }

    public void deletePassenger(Long id) {
        Passenger p = getPassengerById(id);
        passengerRepository.delete(p);
    }

    // ========== TICKET OPERATIONS ==========
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found: " + id));
    }

    public List<Ticket> getTicketsByPassenger(Long passengerId) {
        return ticketRepository.findByPassengerId(passengerId);
    }

    public List<Ticket> getTicketsByClerk(Long clerkId) {
        return ticketRepository.findByBookedByClerkId(clerkId);
    }

    public List<Ticket> getSchedule(Long passengerId) {
        return ticketRepository.findByPassengerIdAndStatus(
            passengerId, Ticket.TicketStatus.PURCHASED);
    }

    // ========== CLERK OPERATIONS ==========
    public TicketClerk addClerk(TicketClerk clerk) {
        if (clerkRepository.findByEmployeeId(clerk.getEmployeeId()).isPresent()) {
            throw new RuntimeException("Employee ID already exists");
        }
        return clerkRepository.save(clerk);
    }

    public List<TicketClerk> getAllClerks() {
        return clerkRepository.findAll();
    }

    public TicketClerk getClerkById(Long id) {
        return clerkRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Clerk not found: " + id));
    }

    // ========== PAYMENT OPERATIONS ==========
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentByTicketId(Long ticketId) {
        return paymentRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new RuntimeException("Payment not found for ticket: " + ticketId));
    }

    // ========== STATISTICS ==========
    public long getTotalTrains()     { return trainRepository.count();     }
    public long getTotalPassengers() { return passengerRepository.count(); }
    public long getTotalTickets()    { return ticketRepository.count();    }
    public long getTotalPayments()   { return paymentRepository.count();   }
    public long getTotalClerks()     { return clerkRepository.count();     }
}