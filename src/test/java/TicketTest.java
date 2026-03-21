import com.anupam1897.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket("TKT001", "Chennai", "Mumbai", 1, 850.0);
    }

    // ── Test 1: Ticket created with correct ticket number ─────────
    @Test
    void testTicketNumber() {
        assertEquals("TKT001", ticket.getTicketNo());
    }

    // ── Test 2: Ticket created with correct starting location ─────
    @Test
    void testStartingLocation() {
        assertEquals("Chennai", ticket.getStartingLocation());
    }

    // ── Test 3: Ticket created with correct destination ───────────
    @Test
    void testDestinationLocation() {
        assertEquals("Mumbai", ticket.getDestinationLocation());
    }

    // ── Test 4: Ticket created with correct passenger number ──────
    @Test
    void testPassengerNo() {
        assertEquals(1, ticket.getPassengerNo());
    }

    // ── Test 5: Ticket is active by default ───────────────────────
    @Test
    void testTicketIsActiveByDefault() {
        assertFalse(ticket.isCancelled());
    }

    // ── Test 6: Payment amount matches fare ───────────────────────
    @Test
    void testPaymentAmount() {
        assertEquals(850.0, ticket.paymentAmount());
    }

    // ── Test 7: Cancel ticket sets cancelled to true ──────────────
    @Test
    void testCancelTicket() {
        ticket.cancelTicket();
        assertTrue(ticket.isCancelled());
    }

    // ── Test 8: Double cancel does not crash ──────────────────────
    @Test
    void testDoubleCancelDoesNotCrash() {
        ticket.cancelTicket();
        assertDoesNotThrow(() -> ticket.cancelTicket());
        assertTrue(ticket.isCancelled());
    }

    // ── Test 9: toString contains ticket number ───────────────────
    @Test
    void testToStringContainsTicketNo() {
        assertTrue(ticket.toString().contains("TKT001"));
    }

    // ── Test 10: toString contains locations ──────────────────────
    @Test
    void testToStringContainsLocations() {
        assertTrue(ticket.toString().contains("Chennai"));
        assertTrue(ticket.toString().contains("Mumbai"));
    }

    // ── Test 11: toString contains fare ───────────────────────────
    @Test
    void testToStringContainsFare() {
        assertTrue(ticket.toString().contains("850.0"));
    }

    // ── Test 12: toString shows Active status by default ──────────
    @Test
    void testToStringShowsActiveStatus() {
        assertTrue(ticket.toString().contains("Active"));
    }

    // ── Test 13: toString shows Cancelled after cancellation ──────
    @Test
    void testToStringShowsCancelledStatus() {
        ticket.cancelTicket();
        assertTrue(ticket.toString().contains("Cancelled"));
    }

    // ── Test 14: Two tickets with different numbers are different ──
    @Test
    void testTwoTicketsAreDifferent() {
        Ticket ticket2 = new Ticket("TKT002", "Delhi", "Kolkata", 2, 600.0);
        assertNotEquals(ticket.getTicketNo(),          ticket2.getTicketNo());
        assertNotEquals(ticket.getStartingLocation(),  ticket2.getStartingLocation());
        assertNotEquals(ticket.getDestinationLocation(), ticket2.getDestinationLocation());
        assertNotEquals(ticket.paymentAmount(),        ticket2.paymentAmount());
    }

    // ── Test 15: Cancelling one ticket doesn't affect another ─────
    @Test
    void testCancelOneTicketDoesNotAffectAnother() {
        Ticket ticket2 = new Ticket("TKT002", "Delhi", "Kolkata", 2, 600.0);
        ticket.cancelTicket();
        assertFalse(ticket2.isCancelled());
    }
}