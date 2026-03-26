package com.anupam1897.model;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainTest {

    @Test
    void testReserveSeat() {
        Train train = new Train("T1","Express","A","B",
                LocalTime.now(), LocalTime.now().plusHours(2),
                2,100);

        assertTrue(train.reserveSeat());
        assertEquals(1, train.getAvailableSeats());

        assertTrue(train.reserveSeat());
        assertEquals(0, train.getAvailableSeats());

        assertFalse(train.reserveSeat());
    }

    @Test
    void testReleaseSeat() {
        Train train = new Train("T1","Express","A","B",
                null,null,2,100);

        train.reserveSeat();
        train.releaseSeat();

        assertEquals(2, train.getAvailableSeats());
    }

    @Test
    void testHasAvailableSeats() {
        Train train = new Train("T1","Express","A","B",
                null,null,1,100);

        assertTrue(train.hasAvailableSeats());

        train.reserveSeat();

        assertFalse(train.hasAvailableSeats());
    }
}