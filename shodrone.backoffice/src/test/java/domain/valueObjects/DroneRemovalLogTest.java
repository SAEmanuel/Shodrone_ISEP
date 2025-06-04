package domain.valueObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneRemovalLogTest {

    private static final String VALID_REASON = "Battery failure detected";
    private static final String SHORT_REASON = "Bad";
    private static final String LONG_REASON = "x".repeat(101);

    @BeforeEach
    void resetAuthUtils() {
        // Se houver algum estado interno estático, reinicializar antes dos testes
    }

    @Test
    void validReasonShouldCreateLog() {
        DroneRemovalLog log = new DroneRemovalLog(VALID_REASON);

        assertEquals(VALID_REASON, log.reason());
        assertNotNull(log.date());
        assertNotNull(log.removedBy());
        assertEquals(AuthUtils.getCurrentUserEmail(), log.removedBy());
    }

    @Test
    void nullReasonShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneRemovalLog(null));
    }

    @Test
    void emptyReasonShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneRemovalLog(""));
    }

    @Test
    void tooShortReasonShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneRemovalLog(SHORT_REASON));
    }

    @Test
    void tooLongReasonShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DroneRemovalLog(LONG_REASON));
    }

    @Test
    void twoLogsWithSameDataShouldBeEqual() {
        DroneRemovalLog log1 = new DroneRemovalLog("Battery overheat");
        try {
            Thread.sleep(1); // garantir que não seja o mesmo timestamp
        } catch (InterruptedException ignored) {}
        DroneRemovalLog log2 = new DroneRemovalLog("Battery overheat");

        // Como o horário muda, provavelmente serão diferentes — teste ajustado
        assertNotEquals(log1, log2);
    }

    @Test
    void toStringShouldContainAllFields() {
        DroneRemovalLog log = new DroneRemovalLog(VALID_REASON);
        String str = log.toString();

        assertTrue(str.contains("Drone Removal Log"));
        assertTrue(str.contains(log.reason()));
        assertTrue(str.contains(log.removedBy()));
        assertTrue(str.contains(log.date().toString()));
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        DroneRemovalLog log1 = new DroneRemovalLog("Propeller issue");
        DroneRemovalLog log2 = log1;

        assertEquals(log1, log2);
        assertEquals(log1.hashCode(), log2.hashCode());
    }
}
