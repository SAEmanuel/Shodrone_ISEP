package figure_dsl.validator;

import figure_dsl.generated.dslLexer;
import figure_dsl.generated.dslParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main validation plugin class for the DSL processing.
// It maintains state during parsing and validation and collects errors.
@Getter
public class FigureValidationPlugin {
    // Drone model name (e.g., the declared drone type)
    @Setter
    private String droneModelName;

    // DSL version string (e.g., "1.0.0")
    private String dslVersion;

    // Number of drone model declarations found (should be exactly one)
    private int droneModelCount;

    // List of error messages found during validation
    private final List<String> errors;

    // Map of declared variables grouped by type (e.g., "DroneType", "Position", etc.)
    // Each type maps variable names to empty strings (used as a set)
    private final Map<String, Map<String, String>> declaredVariables;

    // List of declared element names (from element definitions)
    private final List<String> declaredElements;

    // List of unscoped statements found in the DSL
    private final List<String> unscopedStatements;


    public FigureValidationPlugin() {
        this.dslVersion = null;
        this.droneModelName = null;
        this.droneModelCount = 0;
        this.errors = new ArrayList<>();
        this.declaredVariables = new HashMap<>();

        // Initialize variable maps for known categories
        this.declaredVariables.put("DroneType", new HashMap<>());
        this.declaredVariables.put("Position", new HashMap<>());
        this.declaredVariables.put("Velocity", new HashMap<>());
        this.declaredVariables.put("Distance", new HashMap<>());

        this.declaredElements = new ArrayList<>();
        this.unscopedStatements = new ArrayList<>();
    }

    // Main entry point for validating a list of DSL lines (strings)
    public List<String> validate(List<String> dslLines) {
        resetState();
        String dslText = String.join("\n", dslLines);

        try {
            // Create a CharStream from the input DSL text
            CharStream charStream = CharStreams.fromString(dslText);

            // Create lexer and parser instances from the CharStream
            dslLexer lexer = new dslLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            dslParser parser = new dslParser(tokens);

            // Remove default error listeners to avoid printing errors to console
            lexer.removeErrorListeners();
            parser.removeErrorListeners();

            // Attach custom error listener to collect errors in this plugin
            ErrorListener errorListener = new ErrorListener(this);
            lexer.addErrorListener(errorListener);
            parser.addErrorListener(errorListener);

            // Parse the input DSL text into a parse tree starting at rule 'dsl'
            dslParser.DslContext parseTree = parser.dsl();

            // If no syntax errors found, run semantic validators
            if (errors.isEmpty()) {
                // Validate DSL version declaration
                DslVersionValidator versionValidator = new DslVersionValidator(this);
                versionValidator.visit(parseTree);

                // Validate Drone model declaration
                DroneModelValidator droneValidator = new DroneModelValidator(this);
                droneValidator.visit(parseTree);

                // Validate declared variables (Position, Velocity, etc.)
                DeclaredVariablesValidator varValidator = new DeclaredVariablesValidator(this);
                varValidator.visit(parseTree);

                // Validate element definitions
                ElementDefinitionValidator elementDefinitionValidator = new ElementDefinitionValidator(this);
                elementDefinitionValidator.visit(parseTree);

                // Validate statement blocks
                StatementBlockValidator stmtValidator = new StatementBlockValidator(this);
                stmtValidator.visit(parseTree);

                // Validate unscoped statements
                UnscopedStatementValidator unscopedStatementValidator = new UnscopedStatementValidator(this);
                unscopedStatementValidator.visit(parseTree);

                // Ensure exactly one Drone model is declared
                validateDroneModelPresence();
            }

        } catch (Exception e) {
            errors.add("Critical error during validation: " + e.getMessage());
        }

        return errors;
    }

    // Custom ANTLR error listener class that collects syntax errors into the plugin's error list
    private static class ErrorListener extends BaseErrorListener {
        private final FigureValidationPlugin plugin;

        public ErrorListener(FigureValidationPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            // Format and add the syntax error to the plugin's error list
            plugin.addError(String.format("[Syntax Error] Line %d:%d - %s", line, charPositionInLine, msg));
        }
    }

    // Setter method to update the DSL version string
    void setDslVersion(String version) {
        this.dslVersion = version;
    }

    // Checks if the DSL is valid (no errors recorded)
    public boolean isValid() {
        return errors.isEmpty();
    }

    // Adds a declared drone model name to the DroneType variables map
    void addDroneModel(String modelName) {
        declaredVariables.get("DroneType").put(modelName, "");
    }

    // Adds a declared element name to the list of elements
    public void addDeclaredElement(String elementName) {
        declaredElements.add(elementName);
    }

    // Resets the internal state before each validation run
    private void resetState() {
        droneModelName = null;
        droneModelCount = 0;
        errors.clear();

        // Clear all declared variables by type
        declaredVariables.forEach((k, v) -> v.clear());

        declaredElements.clear();
        unscopedStatements.clear();
    }

    // Ensures that exactly one drone model declaration exists, else adds errors
    private void validateDroneModelPresence() {
        if (droneModelCount == 0) {
            errors.add("[Semantic Error] No 'DroneType' declaration found");
        } else if (droneModelCount > 1) {
            errors.add("[Semantic Error] Multiple 'DroneType' declarations found");
        }
    }

    // Increment the count of drone model declarations found
    void incrementDroneModelCount() {
        droneModelCount++;
    }

    // Adds an error message to the errors list
    void addError(String error) {
        errors.add(error);
    }
}
