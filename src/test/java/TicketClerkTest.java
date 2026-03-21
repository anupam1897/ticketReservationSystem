import com.anupam1897.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketClerkTest {

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

    // ── Test 1: reserveSeat returns a valid ticket ─────────────────
    @Test
    void testReserveSeatReturnsTicket() {
        Ticket ticket = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertNotNull(ticket);
    }

    // ── Test 2: reserveSeat stores ticket in database ──────────────
    @Test
    void testReserveSeatStoresInDatabase() {
        clerk.reserveSeat(passenger, train, "Chennai", "Mumbai", 850.0);
        assertEquals(1, db.getAllTickets().size());
    }

    // ── Test 3: reserveSeat correct starting location ──────────────
    @Test
    void testReserveSeatStartingLocation() {
        Ticket ticket = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertEquals("Chennai", ticket.getStartingLocation());
    }

    // ── Test 4: reserveSeat correct destination ────────────────────
    @Test
    void testReserveSeatDestination() {
        Ticket ticket = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertEquals("Mumbai", ticket.getDestinationLocation());
    }

    // ── Test 5: reserveTicket returns a valid ticket ───────────────
    @Test
    void testReserveTicketReturnsTicket() {
        Ticket ticket = clerk.reserveTicket(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        assertNotNull(ticket);
    }

    // ── Test 6: reserveTicket and reserveSeat behave the same ──────
    @Test
    void testReserveTicketAndReserveSeatBehaveSame() {
        Passenger p2 = new Passenger("Ravi", 30, "Mumbai");
        Ticket t1 = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        Ticket t2 = clerk.reserveTicket(
            p2, train, "Chennai", "Mumbai", 850.0
        );
        assertEquals(t1.getStartingLocation(),    t2.getStartingLocation());
        assertEquals(t1.getDestinationLocation(), t2.getDestinationLocation());
        assertEquals(t1.paymentAmount(),          t2.paymentAmount());
    }

    // ── Test 7: cancelTicket marks ticket as cancelled ─────────────
    @Test
    void testCancelTicketMarksCancelled() {
        Ticket ticket = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        clerk.cancelTicket(passenger, ticket);
        assertTrue(ticket.isCancelled());
    }

    // ── Test 8: cancelTicket removes ticket from database ──────────
    @Test
    void testCancelTicketRemovesFromDatabase() {
        Ticket ticket = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        clerk.cancelTicket(passenger, ticket);
        assertEquals(0, db.getAllTickets().size());
    }

    // ── Test 9: receivePayment updates database response ───────────
    @Test
    void testReceivePaymentUpdatesResponse() {
        clerk.receivePayment(850.0);
        assertEquals(
            "Payment of Rs.850.0 received by Ravi",
            db.getResponse()
        );
    }

    // ── Test 10: receivePayment does not throw ─────────────────────
    @Test
    void testReceivePaymentDoesNotThrow() {
        assertDoesNotThrow(() -> clerk.receivePayment(500.0));
    }

    // ── Test 11: Multiple tickets stored independently ─────────────
    @Test
    void testMultipleTicketsStoredIndependently() {
        Passenger p2 = new Passenger("Ravi", 30, "Mumbai");
        clerk.reserveSeat(passenger, train, "Chennai", "Mumbai", 850.0);
        clerk.reserveSeat(p2,        train, "Delhi",   "Pune",   600.0);
        assertEquals(2, db.getAllTickets().size());
    }

    // ── Test 12: Cancel one ticket leaves others intact ────────────
    @Test
    void testCancelOneTicketLeavesOthersIntact() {
        Passenger p2 = new Passenger("Ravi", 30, "Mumbai");
        Ticket t1 = clerk.reserveSeat(
            passenger, train, "Chennai", "Mumbai", 850.0
        );
        Ticket t2 = clerk.reserveSeat(
            p2, train, "Delhi", "Pune", 600.0
        );
        clerk.cancelTicket(passenger, t1);
        assertEquals(1,       db.getAllTickets().size());
        assertFalse(t2.isCancelled());
    }
}