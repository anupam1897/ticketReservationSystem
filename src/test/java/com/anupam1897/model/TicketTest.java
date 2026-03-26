package com.anupam1897.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void testInitialState() {
        Ticket ticket = new Ticket();
        assertTrue(ticket.isReserved());
    }

    @Test
    void testPurchase() {
        Ticket ticket = new Ticket();

        ticket.purchase();

        assertTrue(ticket.isPurchased());
        assertFalse(ticket.isReserved());
    }

    @Test
    void testCancel() {
        Ticket ticket = new Ticket();

        ticket.cancel();

        assertTrue(ticket.isCancelled());
    }

    @Test
    void testStatusTransition() {
        Ticket ticket = new Ticket();

        ticket.purchase();
        assertTrue(ticket.isPurchased());

        ticket.cancel();
        assertTrue(ticket.isCancelled());
    }
}