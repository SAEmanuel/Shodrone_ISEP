package drone_language_validation;

import lombok.Setter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

public class DroneGenericPlugin {
    @Setter
    private String droneLanguageVersion;
    private final List<String> errors;
    private final Set<String> declaredTypes;
    private final Set<String> declaredInstructions;
    private final Map<String, Map<String, String>> declaredVariables;

    public DroneGenericPlugin(){
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
        this.declaredTypes = new  HashSet<>();
    }

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

            dslParser.DslContext parseTree = parser.dsl();

            if (errors.isEmpty()) {
                DslVersionValidator versionValidator = new DslVersionValidator(this);
                versionValidator.visit(parseTree);

                DroneModelValidator droneValidator = new DroneModelValidator(this);
                droneValidator.visit(parseTree);

                DeclaredVariablesValidator varValidator = new DeclaredVariablesValidator(this);
                varValidator.visit(parseTree);

                ElementDefinitionValidator elementDefinitionValidator = new ElementDefinitionValidator(this);
                elementDefinitionValidator.visit(parseTree);

                StatementBlockValidator stmtValidator = new StatementBlockValidator(this);
                stmtValidator.visit(parseTree);

                UnscopedStatementValidator unscopedStatementValidator = new UnscopedStatementValidator(this);
                unscopedStatementValidator.visit(parseTree);

                validateDroneModelPresence();
            }

        } catch (Exception e) {
            errors.add("Critical error during validation: " + e.getMessage());
        }

        return errors;

    }

    private void resetState() {
        this.declaredTypes.clear();
        this.declaredInstructions.clear();
        this.errors.clear();
        this.declaredVariables.forEach((k, v) -> v.clear());
    }
}
