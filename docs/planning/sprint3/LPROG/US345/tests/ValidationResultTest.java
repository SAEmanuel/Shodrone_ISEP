import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidationResultTest {

    @Test
    public void testValidationResult_Getters() {
        ValidationResult result = new ValidationResult(true, "Success");
        assertTrue(result.isValid());
        assertEquals("Success", result.getMessage());

        ValidationResult errorResult = new ValidationResult(false, "Failure");
        assertFalse(errorResult.isValid());
        assertEquals("Failure", errorResult.getMessage());
    }
}
