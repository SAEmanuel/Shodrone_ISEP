package drone_language_validation;

import drone_language_validation.generated.droneGenericLexer;
import drone_language_validation.generated.droneGenericParser;
import lombok.Setter;
import org.antlr.v4.runtime.*;

import javax.xml.transform.ErrorListener;
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

            droneGenericParser.ProgramContext parseTree = parser.program();


            if (errors.isEmpty()) {

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
}
