package com.anupam1897.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    @Test
    void testConstructor() {
        Passenger p = new Passenger("John",25,"M",
                "Addr","123","john@test.com");

        assertEquals("John", p.getName());
        assertEquals(25, p.getAge());
        assertEquals("john@test.com", p.getEmail());
    }

    @Test
    void testSettersGetters() {
        Passenger p = new Passenger();

        p.setName("Alice");
        p.setAge(30);

        assertEquals("Alice", p.getName());
        assertEquals(30, p.getAge());
    }
}