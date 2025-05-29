import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestDronePlugin {

    @Test
    public void testAnalyzeProgram_ValidCode() {
        String code = "Position pos = (1, 2, 3); LinearVelocity lv = 10 + 5; Move(<target>);";

        DroneLanguagePlugin plugin = new DroneOnePlugin();
        ValidationResult result = plugin.analyzeProgram(code);

        assertTrue(result.isValid());
        assertEquals("Programa válido!", result.getMessage());
    }

    @Test
    public void testAnalyzeProgram_SyntaxError() {
        String code = "Position pos = (1, 2, ;";  // Malformed tuple

        DroneLanguagePlugin plugin = new DroneOnePlugin();
        ValidationResult result = plugin.analyzeProgram(code);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Erro(s) de sintaxe"));
    }

    @Test
    public void testAnalyzeProgram_ExceptionHandling() {
        String code = null;  // Passing null to cause exception

        DroneLanguagePlugin plugin = new DroneOnePlugin();
        ValidationResult result = plugin.analyzeProgram(code);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Erro na análise"));
    }
}
