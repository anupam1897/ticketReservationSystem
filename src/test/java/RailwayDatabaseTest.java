import com.anupam1897.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RailwayDatabaseTest {

    private RailwayDatabase db;
    private Train train1;
    private Train train2;
    private Ticket ticket1;
    private Ticket ticket2;

    @BeforeEach
    void setUp() {
        db      = new RailwayDatabase("DB001");
        train1  = new Train("Express",   "T101");
        train2  = new Train("Superfast", "T202");
        ticket1 = new Ticket("TKT001", "Chennai", "Mumbai",  1, 850.0);
        ticket2 = new Ticket("TKT002", "Delhi",   "Kolkata", 2, 600.0);
    }

    // ── Test 1: Database created with correct ID ──────────────────
    @Test
    void testDatabaseCreation() {
        assertEquals("DB001", db.getId());
    }

    // ── Test 2: Add single train ──────────────────────────────────
    @Test
    void testAddSingleTrain() {
        db.addTrain(train1);
        assertEquals(1, db.getAllTrains().size());
        assertEquals("T101", db.getAllTrains().get(0).getTrainId());
    }

    // ── Test 3: Add multiple trains ───────────────────────────────
    @Test
    void testAddMultipleTrains() {
        db.addTrain(train1);
        db.addTrain(train2);
        assertEquals(2, db.getAllTrains().size());
    }

    // ── Test 4: Add train updates response ────────────────────────
    @Test
    void testAddTrainUpdatesResponse() {
        db.addTrain(train1);
        assertEquals("Train added: T101", db.getResponse());
    }

    // ── Test 5: Add single ticket ─────────────────────────────────
    @Test
    void testAddSingleTicket() {
        db.addTicket(ticket1);
        assertEquals(1, db.getAllTickets().size());
        assertEquals("TKT001", db.getAllTickets().get(0).getTicketNo());
    }

    // ── Test 6: Add multiple tickets ──────────────────────────────
    @Test
    void testAddMultipleTickets() {
        db.addTicket(ticket1);
        db.addTicket(ticket2);
        assertEquals(2, db.getAllTickets().size());
    }

    // ── Test 7: Add ticket updates response ───────────────────────
    @Test
    void testAddTicketUpdatesResponse() {
        db.addTicket(ticket1);
        assertEquals("Ticket stored: TKT001", db.getResponse());
    }

    // ── Test 8: Remove existing ticket returns true ───────────────
    @Test
    void testRemoveExistingTicket() {
        db.addTicket(ticket1);
        boolean result = db.removeTicket("TKT001");
        assertTrue(result);
        assertEquals(0, db.getAllTickets().size());
    }

    // ── Test 9: Remove non-existing ticket returns false ─────────
    @Test
    void testRemoveNonExistingTicket() {
        boolean result = db.removeTicket("TKT999");
        assertFalse(result);
    }

    // ── Test 10: Remove ticket updates response correctly ─────────
    @Test
    void testRemoveTicketUpdatesResponse() {
        db.addTicket(ticket1);
        db.removeTicket("TKT001");
        assertEquals("Ticket removed: TKT001", db.getResponse());
    }

    // ── Test 11: Remove missing ticket response says not found ────
    @Test
    void testRemoveMissingTicketResponse() {
        db.removeTicket("TKT999");
        assertEquals("Ticket not found: TKT999", db.getResponse());
    }

    // ── Test 12: setResponse works correctly ──────────────────────
    @Test
    void testSetResponse() {
        db.setResponse("Custom response");
        assertEquals("Custom response", db.getResponse());
    }

    // ── Test 13: Empty DB has no trains ───────────────────────────
    @Test
    void testEmptyDatabaseHasNoTrains() {
        assertEquals(0, db.getAllTrains().size());
    }

    // ── Test 14: Empty DB has no tickets ──────────────────────────
    @Test
    void testEmptyDatabaseHasNoTickets() {
        assertEquals(0, db.getAllTickets().size());
    }

    // ── Test 15: Remove one ticket leaves others intact ───────────
    @Test
    void testRemoveOneTicketLeavesOthersIntact() {
        db.addTicket(ticket1);
        db.addTicket(ticket2);
        db.removeTicket("TKT001");
        assertEquals(1, db.getAllTickets().size());
        assertEquals("TKT002", db.getAllTickets().get(0).getTicketNo());
    }
}