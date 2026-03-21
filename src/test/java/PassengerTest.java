import com.anupam1897.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PassengerTest {

    private RailwayDatabase db;
    private Passenger passenger;
    private TicketClerk clerk;
    private Train train;

    @BeforeEach
    void setUp() {
        db        = new RailwayDatabase("DB001");
        train     = new Train("Express", "T101");
        db.addTrain(train);
        passenger = new Passenger("Anupam", 22, "Chennai");
        clerk     = new TicketClerk("Ravi", "Chennai Central", db);
    }

    // ── Test 1: Passenger object is created with correct details ──
    @Test
    void testPassengerCreation() {
        assertEquals("Anupam",  passenger.getName());
        assertEquals(22,        passenger.getAge());
        assertEquals("Chennai", passenger.getAddress());
    }

    // ── Test 2: Passenger gets a unique ID ────────────────────────
    @Test
    void testPassengerUniqueId() {
        Passenger p2 = new Passenger("Ravi", 30, "Mumbai");
        assertNotEquals(passenger.getPassengerId(), p2.getPassengerId());
    }

    // ── Test 3: Passenger can reserve a seat ──────────────────────
    @Test
    void testPassengerReserveSeat() {
        Ticket ticket = passenger.reserveSeat(train, "Chennai", "Mumbai", 850.0);
        assertNotNull(ticket);
        assertEquals("Chennai", ticket.getStartingLocation());
        assertEquals("Mumbai",  ticket.getDestinationLocation());
        assertFalse(ticket.isCancelled());
    }

    // ── Test 4: Passenger can cancel a ticket ─────────────────────
    @Test
    void testPassengerCancelTicket() {
        Ticket ticket = passenger.reserveSeat(train, "Chennai", "Mumbai", 850.0);
        passenger.cancelTicket(ticket);
        assertTrue(ticket.isCancelled());
    }

    // ── Test 5: Passenger can make a payment ──────────────────────
    @Test
    void testPassengerMakePayment() {
        Ticket ticket = passenger.reserveSeat(train, "Chennai", "Mumbai", 750.0);
        Payment payment = new Payment(ticket.paymentAmount());
        assertEquals(750.0, payment.getAmount());
        assertDoesNotThrow(() -> passenger.makePayment(750.0));
    }

    // ── Test 6: Passenger can search trains ───────────────────────
    @Test
    void testPassengerSearchTrains() {
        db.addTrain(new Train("Superfast", "T202"));
        assertDoesNotThrow(() -> passenger.searchTrains(db));
        assertEquals(2, db.getAllTrains().size());
    }

    // ── Test 7: Passenger can purchase a ticket ───────────────────
    @Test
    void testPassengerPurchaseTicket() {
        Ticket ticket   = passenger.reserveSeat(train, "Chennai", "Mumbai", 850.0);
        Payment payment = new Payment(850.0);
        assertDoesNotThrow(() -> passenger.purchaseTicket(ticket, payment));
    }

    // ── Test 8: Passenger view schedule doesn't crash ─────────────
    @Test
    void testPassengerViewSchedule() {
        passenger.reserveSeat(train, "Chennai", "Mumbai", 850.0);
        assertDoesNotThrow(() -> passenger.viewSchedule());
    }
}