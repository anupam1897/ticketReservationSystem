import com.anupam1897.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    // ── Test 1: Payment created with correct amount ───────────────
    @Test
    void testPaymentCreation() {
        Payment payment = new Payment(850.0);
        assertEquals(850.0, payment.getAmount());
    }

    // ── Test 2: Payment with zero amount ──────────────────────────
    @Test
    void testZeroPayment() {
        Payment payment = new Payment(0.0);
        assertEquals(0.0, payment.getAmount());
    }

    // ── Test 3: Payment with decimal amount ───────────────────────
    @Test
    void testDecimalPayment() {
        Payment payment = new Payment(499.99);
        assertEquals(499.99, payment.getAmount());
    }

    // ── Test 4: Payment toString format ───────────────────────────
    @Test
    void testPaymentToString() {
        Payment payment = new Payment(500.0);
        assertEquals("Payment: Rs.500.0", payment.toString());
    }

    // ── Test 5: Two payments are independent ──────────────────────
    @Test
    void testTwoPaymentsAreIndependent() {
        Payment p1 = new Payment(200.0);
        Payment p2 = new Payment(400.0);
        assertNotEquals(p1.getAmount(), p2.getAmount());
    }

    // ── Test 6: Large payment amount ──────────────────────────────
    @Test
    void testLargePaymentAmount() {
        Payment payment = new Payment(99999.99);
        assertEquals(99999.99, payment.getAmount());
    }
}