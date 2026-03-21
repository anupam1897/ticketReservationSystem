import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import com.anupam1897.*;

public class TicketReservationTest {

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

    // ── Test 1: Train is added to database correctly ──────────────
    @Test
    void testTrainAddedToDatabase() {
        assertEquals(1, db.getAllTrains().size());
        assertEquals("T101", db.getAllTrains().get(0).getTrainId());
    }

    // ── Test 2: Ticket is reserved successfully ───────────────────
    @Test
    void testTicketReservation() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertNotNull(ticket);
        assertEquals("Chennai", ticket.getStartingLocation());
        assertEquals("Mumbai",  ticket.getDestinationLocation());
        assertEquals(850.0,     ticket.paymentAmount());
    }

    // ── Test 3: Ticket is stored in database after reservation ────
    @Test
    void testTicketStoredInDatabase() {
        clerk.reserveTicket(passenger, train, "Chennai", "Mumbai", 850.0);
        assertEquals(1, db.getAllTickets().size());
    }

    // ── Test 4: Ticket cancellation works correctly ───────────────
    @Test
    void testTicketCancellation() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        clerk.cancelTicket(passenger, ticket);

        assertTrue(ticket.isCancelled());
        assertEquals(0, db.getAllTickets().size());
    }

    // ── Test 5: Payment amount matches fare ───────────────────────
    @Test
    void testPaymentAmount() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 500.0
        );
        Payment payment = new Payment(ticket.paymentAmount());
        assertEquals(500.0, payment.getAmount());
    }

    // ── Test 6: Cancelling already cancelled ticket ───────────────
    @Test
    void testDoubleCancellation() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        clerk.cancelTicket(passenger, ticket);
        ticket.cancelTicket(); // cancel again
        assertTrue(ticket.isCancelled()); // still cancelled, no crash
    }

    // ── Test 7: Database response updates after actions ───────────
    @Test
    void testDatabaseResponseUpdates() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertNotNull(db.getResponse());
        assertTrue(db.getResponse().contains("Ticket stored"));
    }
}