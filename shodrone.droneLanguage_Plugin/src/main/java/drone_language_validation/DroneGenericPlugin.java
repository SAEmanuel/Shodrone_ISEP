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

public class DroneGenericPlugin {
    @Setter
    private String droneLanguageVersion;
    @Getter
    private final List<String> errors;
    @Getter
    private final Set<String> declaredTypes;
    @Getter
    private final Set<String> declaredInstructions;
    private final Map<String, Map<String, String>> declaredVariables;
    public enum VariableKind { SCALAR, VECTOR, ARRAY }

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



    //---------------------------(Utils Methods)-------------------------------------------

    private void resetState() {
        this.declaredTypes.clear();
        this.declaredInstructions.clear();
        this.errors.clear();
        this.declaredVariables.forEach((k, v) -> v.clear());
    }

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

    public void addError(String error) {
        errors.add(error);
    }

    public void registerVariable(String name, String type, VariableKind kind) {
        Map<String,String> info = new HashMap<>();
        info.put("type", type);
        info.put("kind", kind.name());
        declaredVariables.put(name, info);
    }

    public boolean isVariableDeclared(String name) {
        return declaredVariables.containsKey(name);
    }

    public VariableKind getVariableKind(String name) {
        Map<String,String> info = declaredVariables.get(name);
        return info == null ? null : VariableKind.valueOf(info.get("kind"));
    }

    public String getVariableType(String name) {
        Map<String,String> info = declaredVariables.get(name);
        return info != null ? info.get("type") : null;
    }

}
