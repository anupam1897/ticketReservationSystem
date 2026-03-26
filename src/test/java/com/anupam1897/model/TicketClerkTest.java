package com.anupam1897.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketClerkTest {

    @Test
    void testInitialStats() {
        TicketClerk clerk = new TicketClerk();

        assertEquals(0, clerk.getTotalTicketsProcessed());
        assertEquals(0, clerk.getActiveBookings());
        assertEquals(0, clerk.getCancelledBookings());
    }

    @Test
    void testStatistics() {
        TicketClerk clerk = new TicketClerk();

        Ticket t1 = new Ticket(); // active
        Ticket t2 = new Ticket();
        t2.cancel(); // cancelled

        clerk.setTicketsProcessed(List.of(t1, t2));

        assertEquals(2, clerk.getTotalTicketsProcessed());
        assertEquals(1, clerk.getActiveBookings());
        assertEquals(1, clerk.getCancelledBookings());
    }
}