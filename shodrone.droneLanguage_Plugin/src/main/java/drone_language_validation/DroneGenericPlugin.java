package drone_language_validation;

import drone_language_validation.generated.droneGenericLexer;
import drone_language_validation.generated.droneGenericParser;
import drone_language_validation.validator.InstructionsValidationVisitor;
import drone_language_validation.validator.ProgramLanguageVersionVisitor;
import drone_language_validation.validator.TypesValidationVisitor;
import drone_language_validation.validator.VariablesValidationVisitor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.*;

import java.util.*;

/**
 * Core plugin responsible for validating programs written in the DroneGeneric DSL.
 * Coordinates syntax and semantic validations by delegating to specialized visitors.
 */
public class DroneGenericPlugin {

    /**
     * Language version declared in the program's header.
     */
    @Setter
    private String droneLanguageVersion;

    /**
     * List of errors collected during the validation process.
     */
    @Getter
    private final List<String> errors;

    /**
     * Set of types declared in the 'Types' section.
     */
    @Getter
    private final Set<String> declaredTypes;

    /**
     * Set of instructions used throughout the program.
     */
    @Getter
    private final Set<String> declaredInstructions;

    /**
     * Map of declared variables and their metadata (type and kind).
     * Structure: variable name → { "type": ..., "kind": SCALAR | VECTOR | ARRAY }
     */
    private final Map<String, Map<String, String>> declaredVariables;

    /**
     * Enum representing the kind of a variable.
     */
    public enum VariableKind { SCALAR, VECTOR, ARRAY }

    /**
     * Initializes a new instance of the plugin with default known variable types and empty error state.
     */
    public DroneGenericPlugin() {
        this.droneLanguageVersion = null;
        this.errors = new ArrayList<>();
        this.declaredVariables = new HashMap<>();
        this.declaredVariables.put("Position", new HashMap<>());
        this.declaredVariables.put("Vector ", new HashMap<>());
        this.declaredVariables.put("LinearVelocity", new HashMap<>());
        this.declaredVariables.put("AngularVelocity", new HashMap<>());
        this.declaredVariables.put("Distance", new HashMap<>());
        this.declaredVariables.put("Time", new HashMap<>());
        this.declaredInstructions = new HashSet<>();
        this.declaredTypes = new HashSet<>();
    }

    /**
     * Main validation method. Accepts a list of DSL lines, parses them using ANTLR,
     * and invokes semantic validation visitors. Returns a list of detected errors.
     *
     * @param dslLines List of strings representing the lines of the DSL program
     * @return List of validation error messages
     */
    public List<String> validate(List<String> dslLines) {
        resetState();
        String dslText = String.join("\n", dslLines);

        try {
            CharStream charStream = CharStreams.fromString(dslText);
            droneGenericLexer lexer = new droneGenericLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            droneGenericParser parser = new droneGenericParser(tokens);

            lexer.removeErrorListeners();
            parser.removeErrorListeners();
            ErrorListener errorListener = new ErrorListener(this);
            lexer.addErrorListener(errorListener);
            parser.addErrorListener(errorListener);

            droneGenericParser.ProgramContext parseTree = parser.program();

            if (errors.isEmpty()) {
                ProgramLanguageVersionVisitor versionValidator = new ProgramLanguageVersionVisitor(this);
                versionValidator.visitProgram(parseTree);

                TypesValidationVisitor typesValidator = new TypesValidationVisitor(this);
                typesValidator.visitProgram(parseTree);

                VariablesValidationVisitor variablesValidator = new VariablesValidationVisitor(this);
                variablesValidator.visitProgram(parseTree);

                InstructionsValidationVisitor instructionsValidator = new InstructionsValidationVisitor(this);
                instructionsValidator.visitProgram(parseTree);
            }

        } catch (Exception e) {
            errors.add("Critical error during validation: " + e.getMessage());
        }

        return errors;
    }

    /**
     * Resets the plugin’s internal state, clearing all errors and declared elements.
     */
    private void resetState() {
        this.declaredTypes.clear();
        this.declaredInstructions.clear();
        this.errors.clear();
        this.declaredVariables.forEach((k, v) -> v.clear());
    }

    /**
     * Custom error listener that captures syntax errors and adds them to the error list.
     */
    private static class ErrorListener extends BaseErrorListener {
        private final DroneGenericPlugin plugin;

        public ErrorListener(DroneGenericPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            plugin.addError(String.format("[Syntax Error] Line %d:%d - %s", line, charPositionInLine, msg));
        }
    }

    /**
     * Adds an error message to the plugin’s error list.
     *
     * @param error The error message to add
     */
    public void addError(String error) {
        errors.add(error);
    }

    /**
     * Registers a new variable by storing its type and kind (scalar, vector, or array).
     *
     * @param name Variable name
     * @param type Variable type (e.g., "Vector", "Distance")
     * @param kind Variable kind (SCALAR, VECTOR, ARRAY)
     */
    public void registerVariable(String name, String type, VariableKind kind) {
        Map<String, String> info = new HashMap<>();
        info.put("type", type);
        info.put("kind", kind.name());
        declaredVariables.put(name, info);
    }

    /**
     * Checks whether a variable with the given name has been declared.
     *
     * @param name Variable name
     * @return true if declared, false otherwise
     */
    public boolean isVariableDeclared(String name) {
        return declaredVariables.containsKey(name);
    }

    /**
     * Returns the kind (SCALAR, VECTOR, ARRAY) of a declared variable.
     *
     * @param name Variable name
     * @return VariableKind enum value or null if not declared
     */
    public VariableKind getVariableKind(String name) {
        Map<String, String> info = declaredVariables.get(name);
        return info == null ? null : VariableKind.valueOf(info.get("kind"));
    }

    /**
     * Returns the declared type (e.g., "Time", "Point") of a variable.
     *
     * @param name Variable name
     * @return String representing the type or null if not declared
     */
    public String getVariableType(String name) {
        Map<String, String> info = declaredVariables.get(name);
        return info != null ? info.get("type") : null;
    }
}
