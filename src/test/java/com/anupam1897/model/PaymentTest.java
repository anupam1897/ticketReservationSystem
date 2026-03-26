package com.anupam1897.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testInitialStatus() {
        Payment payment = new Payment();
        assertEquals(Payment.PaymentStatus.PENDING, payment.getStatus());
    }

    @Test
    void testComplete() {
        Payment payment = new Payment();

        payment.complete();

        assertEquals(Payment.PaymentStatus.COMPLETED, payment.getStatus());
    }

    @Test
    void testFail() {
        Payment payment = new Payment();

        payment.fail();

        assertEquals(Payment.PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void testRefund() {
        Payment payment = new Payment();

        payment.refund();

        assertEquals(Payment.PaymentStatus.REFUNDED, payment.getStatus());
    }
}