import com.anupam1897.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {

    private Train train;

    @BeforeEach
    void setUp() {
        train = new Train("Express", "T101");
    }

    // ── Test 1: Train created with correct model ───────────────────
    @Test
    void testTrainModel() {
        assertEquals("Express", train.getModel());
    }

    // ── Test 2: Train created with correct ID ─────────────────────
    @Test
    void testTrainId() {
        assertEquals("T101", train.getTrainId());
    }

    // ── Test 3: toString contains train ID ────────────────────────
    @Test
    void testToStringContainsTrainId() {
        assertTrue(train.toString().contains("T101"));
    }

    // ── Test 4: toString contains model ───────────────────────────
    @Test
    void testToStringContainsModel() {
        assertTrue(train.toString().contains("Express"));
    }

    // ── Test 5: toString correct format ───────────────────────────
    @Test
    void testToStringFormat() {
        assertEquals("Train[T101] - Express", train.toString());
    }

    // ── Test 6: Two trains have different IDs ─────────────────────
    @Test
    void testTwoTrainsHaveDifferentIds() {
        Train train2 = new Train("Superfast", "T202");
        assertNotEquals(train.getTrainId(), train2.getTrainId());
    }

    // ── Test 7: Two trains have different models ───────────────────
    @Test
    void testTwoTrainsHaveDifferentModels() {
        Train train2 = new Train("Superfast", "T202");
        assertNotEquals(train.getModel(), train2.getModel());
    }

    // ── Test 8: Train with same ID and model are equal in values ──
    @Test
    void testTrainValuesMatch() {
        Train train2 = new Train("Express", "T101");
        assertEquals(train.getModel(),   train2.getModel());
        assertEquals(train.getTrainId(), train2.getTrainId());
    }
}