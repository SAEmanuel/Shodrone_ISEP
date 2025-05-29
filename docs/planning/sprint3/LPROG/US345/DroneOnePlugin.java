import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.DiagnosticErrorListener;


public class DroneOnePlugin implements DroneLanguagePlugin {

    @Override
    public ValidationResult analyzeProgram(String code) {
        try {
            // 1. Create a lexer that tokenizes the input code string
            DroneOneLexer lexer = new DroneOneLexer(CharStreams.fromString(code));

            // 2. Use the lexer tokens to create a token stream
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3. Create a parser to parse tokens according to the grammar rules
            DroneOneParser parser = new DroneOneParser(tokens);

            // 4. Remove default error listeners and add a diagnostic listener
            parser.removeErrorListeners();
            DiagnosticErrorListener errorListener = new DiagnosticErrorListener();
            parser.addErrorListener(errorListener);

            // 5. Parse the input according to the 'program' rule, producing a parse tree
            ParseTree tree = parser.program();

            // 6. If there are any syntax errors in parsing, return failure result
            if (parser.getNumberOfSyntaxErrors() > 0) {
                return new ValidationResult(false, "Erro(s) de sintaxe detectado(s)");
            }

            // 7. Create a validator visitor and traverse the parse tree for semantic validation
            DroneOneValidator validator = new DroneOneValidator();
            validator.visit(tree);

            // 8. Return success result (could be extended to report validator errors)
            return new ValidationResult(true, "Programa válido!");

        } catch (Exception e) {
            // 9. Catch any unexpected errors during analysis and report failure
            return new ValidationResult(false, "Erro na análise: " + e.getMessage());
        }
    }
}
