package figure_dsl.validator;

import figure_dsl.generated.dslLexer;
import figure_dsl.generated.dslParser;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class FigureValidationPlugin {
    @Setter
    private String droneModelName;
    private String dslVersion;
    private int droneModelCount;
    private final List<String> errors;
    private final Map<String, Map<String, String>> declaredVariables;
    private final List<String> declaredElements;

    public FigureValidationPlugin() {
        this.dslVersion = null;
        this.droneModelName = null;
        this.droneModelCount = 0;
        this.errors = new ArrayList<>();
        this.declaredVariables = new HashMap<>();
        this.declaredVariables.put("DroneType", new HashMap<>());
        this.declaredVariables.put("Position", new HashMap<>());
        this.declaredVariables.put("Velocity", new HashMap<>());
        this.declaredVariables.put("Distance", new HashMap<>());
        this.declaredElements = new ArrayList<>();
    }

    public List<String> validate(List<String> dslLines) {
        resetState();
        String dslText = String.join("\n", dslLines);

        try {
            CharStream charStream = CharStreams.fromString(dslText);
            dslLexer lexer = new dslLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            dslParser parser = new dslParser(tokens);

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

                validateDroneModelPresence();
            }

        } catch (Exception e) {
            errors.add("Critical error during validation: " + e.getMessage());
        }

        return errors;
    }

    private static class ErrorListener extends BaseErrorListener {
        private final FigureValidationPlugin plugin;

        public ErrorListener(FigureValidationPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            plugin.addError(String.format("[Syntax Error] Line %d:%d - %s", line, charPositionInLine, msg));
        }
    }

    void setDslVersion(String version) {
        this.dslVersion = version;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    void addDroneModel(String modelName) {
        declaredVariables.get("DroneType").put(modelName, "");
    }

    public void addDeclaredElement(String elementName) {
        declaredElements.add(elementName);
    }

    private void resetState() {
        droneModelName = null;
        droneModelCount = 0;
        errors.clear();
        declaredVariables.forEach((k, v) -> v.clear());
        declaredElements.clear();
    }

    private void validateDroneModelPresence() {
        if (droneModelCount == 0) {
            errors.add("[Semantic Error] No 'DroneType' declaration found");
        } else if (droneModelCount > 1) {
            errors.add("[Semantic Error] Multiple 'DroneType' declarations found");
        }
    }

    void incrementDroneModelCount() {
        droneModelCount++;
    }

    void addError(String error) {
        errors.add(error);
    }
}