package com.anupam1897.service;

import com.anupam1897.model.*;
import com.anupam1897.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RailwayDatabaseTest {

    @Mock private TrainRepository trainRepository;
    @Mock private PassengerRepository passengerRepository;
    @Mock private TicketRepository ticketRepository;
    @Mock private PaymentRepository paymentRepository;
    @Mock private TicketClerkRepository clerkRepository;

    @InjectMocks
    private RailwayDatabase db;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ================= TRAIN =================

    @Test
    void addTrain_success() {
        Train train = new Train();
        train.setTrainNumber("T1");
        train.setTotalSeats(100);

        when(trainRepository.findByTrainNumber("T1")).thenReturn(Optional.empty());
        when(trainRepository.save(train)).thenReturn(train);

        Train result = db.addTrain(train);

        assertEquals(100, result.getAvailableSeats());
        verify(trainRepository).save(train);
    }

    @Test
    void addTrain_duplicate_shouldThrow() {
        Train train = new Train();
        train.setTrainNumber("T1");

        when(trainRepository.findByTrainNumber("T1"))
                .thenReturn(Optional.of(new Train()));

        assertThrows(RuntimeException.class, () -> db.addTrain(train));
    }

    @Test
    void getTrainById_success() {
        Train train = new Train();

        when(trainRepository.findById(1L)).thenReturn(Optional.of(train));

        assertNotNull(db.getTrainById(1L));
    }

    @Test
    void getTrainById_notFound() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> db.getTrainById(1L));
    }

    @Test
    void searchTrains_allBranches() {
        when(trainRepository.findBySourceAndDestination("A","B"))
                .thenReturn(List.of(new Train()));
        when(trainRepository.findBySource("A"))
                .thenReturn(List.of(new Train()));
        when(trainRepository.findByDestination("B"))
                .thenReturn(List.of(new Train()));
        when(trainRepository.findAll())
                .thenReturn(List.of(new Train()));

        assertEquals(1, db.searchTrains("A","B").size());
        assertEquals(1, db.searchTrains("A",null).size());
        assertEquals(1, db.searchTrains(null,"B").size());
        assertEquals(1, db.searchTrains(null,null).size());
    }

    @Test
    void deleteTrain() {
        Train train = new Train();

        when(trainRepository.findById(1L)).thenReturn(Optional.of(train));

        db.deleteTrain(1L);

        verify(trainRepository).delete(train);
    }

    // ================= PASSENGER =================

    @Test
    void addPassenger_success() {
        Passenger p = new Passenger();

        when(passengerRepository.save(p)).thenReturn(p);

        assertNotNull(db.addPassenger(p));
    }

    @Test
    void addPassenger_duplicateEmail() {
        Passenger p = new Passenger();
        p.setEmail("test@mail.com");

        when(passengerRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(new Passenger()));

        assertThrows(RuntimeException.class, () -> db.addPassenger(p));
    }

    @Test
    void getPassengerById_success() {
        when(passengerRepository.findById(1L))
                .thenReturn(Optional.of(new Passenger()));

        assertNotNull(db.getPassengerById(1L));
    }

    @Test
    void getPassengerById_notFound() {
        when(passengerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> db.getPassengerById(1L));
    }

    @Test
    void deletePassenger() {
        Passenger p = new Passenger();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(p));

        db.deletePassenger(1L);

        verify(passengerRepository).delete(p);
    }

    // ================= TICKET =================

    @Test
    void getTicketById_success() {
        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(new Ticket()));

        assertNotNull(db.getTicketById(1L));
    }

    @Test
    void getTicketById_notFound() {
        when(ticketRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> db.getTicketById(1L));
    }

    @Test
    void getTicketsByPassenger() {
        when(ticketRepository.findByPassengerId(1L))
                .thenReturn(List.of(new Ticket()));

        assertEquals(1, db.getTicketsByPassenger(1L).size());
    }

    @Test
    void getTicketsByClerk() {
        when(ticketRepository.findByBookedByClerkId(1L))
                .thenReturn(List.of(new Ticket()));

        assertEquals(1, db.getTicketsByClerk(1L).size());
    }

    @Test
    void getSchedule() {
        when(ticketRepository.findByPassengerIdAndStatus(eq(1L), eq(Ticket.TicketStatus.PURCHASED)))
                .thenReturn(List.of(new Ticket()));

        assertEquals(1, db.getSchedule(1L).size());
    }

    // ================= CLERK =================

    @Test
    void addClerk_success() {
        TicketClerk clerk = new TicketClerk();
        clerk.setEmployeeId("EMP1");

        when(clerkRepository.findByEmployeeId("EMP1")).thenReturn(Optional.empty());
        when(clerkRepository.save(clerk)).thenReturn(clerk);

        assertNotNull(db.addClerk(clerk));
    }

    @Test
    void addClerk_duplicate() {
        TicketClerk clerk = new TicketClerk();
        clerk.setEmployeeId("EMP1");

        when(clerkRepository.findByEmployeeId("EMP1"))
                .thenReturn(Optional.of(new TicketClerk()));

        assertThrows(RuntimeException.class, () -> db.addClerk(clerk));
    }

    @Test
    void getClerkById_success() {
        when(clerkRepository.findById(1L))
                .thenReturn(Optional.of(new TicketClerk()));

        assertNotNull(db.getClerkById(1L));
    }

    @Test
    void getClerkById_notFound() {
        when(clerkRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> db.getClerkById(1L));
    }

    // ================= PAYMENT =================

    @Test
    void getPaymentByTicketId_success() {
        when(paymentRepository.findByTicketId(1L))
                .thenReturn(Optional.of(new Payment()));

        assertNotNull(db.getPaymentByTicketId(1L));
    }

    @Test
    void getPaymentByTicketId_notFound() {
        when(paymentRepository.findByTicketId(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> db.getPaymentByTicketId(1L));
    }

    // ================= STATS =================

    @Test
    void statisticsCounts() {
        when(trainRepository.count()).thenReturn(5L);
        when(passengerRepository.count()).thenReturn(10L);
        when(ticketRepository.count()).thenReturn(15L);
        when(paymentRepository.count()).thenReturn(20L);
        when(clerkRepository.count()).thenReturn(2L);

        assertEquals(5, db.getTotalTrains());
        assertEquals(10, db.getTotalPassengers());
        assertEquals(15, db.getTotalTickets());
        assertEquals(20, db.getTotalPayments());
        assertEquals(2, db.getTotalClerks());
    }
}