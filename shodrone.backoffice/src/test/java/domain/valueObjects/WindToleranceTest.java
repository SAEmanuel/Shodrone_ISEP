package domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindToleranceTest {

    @Test
    void validWindToleranceShouldBeAccepted() {
        WindTolerance wt = new WindTolerance(0, 10, 1, 1, 1, 15);
        assertEquals(0, wt.getMinWindSpeed());
        assertEquals(10, wt.getMaxWindSpeed());
        assertEquals(1, wt.getXTolerance());
        assertEquals(1, wt.getYTolerance());
        assertEquals(1, wt.getZTolerance());
    }

    @Test
    void minWindSpeedCannotBeGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(15, 10, 1, 1, 1, 20);
        });
    }

    @Test
    void maxWindSpeedCannotExceedModelLimit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 20, 1, 1, 1, 15);
        });
    }

    @Test
    void nullValuesShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(null, 10, 1, 1, 1, 15);
        });

        assertThrows(NullPointerException.class, () -> {
            new WindTolerance(0, null, 1, 1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, null, 1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, 1, null, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, 1, 1, null, 15);
        });
    }

    @Test
    void negativeValuesShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(-1, 10, 1, 1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, -10, 1, 1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, -1, 1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, 1, -1, 1, 15);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new WindTolerance(0, 10, 1, 1, -1, 15);
        });
    }

    @Test
    void equalWindTolerancesShouldBeEqual() {
        WindTolerance wt1 = new WindTolerance(0, 10, 1, 1, 1, 15);
        WindTolerance wt2 = new WindTolerance(0, 10, 1, 1, 1, 15);
        assertEquals(wt1, wt2);
        assertEquals(wt1.hashCode(), wt2.hashCode());
    }

    @Test
    void differentWindTolerancesShouldNotBeEqual() {
        WindTolerance wt1 = new WindTolerance(0, 10, 1, 1, 1, 15);
        WindTolerance wt2 = new WindTolerance(0, 10, 2, 2, 2, 15);
        assertNotEquals(wt1, wt2);
    }

}
