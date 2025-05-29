import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DroneOneParserTest {

    private ParseTree parse(String code) throws Exception {
        CharStream input = CharStreams.fromString(code);
        DroneOneLexer lexer = new DroneOneLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DroneOneParser parser = new DroneOneParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                throw new RuntimeException("syntax error at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });
        return parser.program();
    }

    @Test
    void testValidPositionDeclaration() throws Exception {
        String code = "Position startPos = (0,0,0);";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidVectorDeclaration() throws Exception {
        String code = "Vector velocity = (1,2,3);";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidInstruction() throws Exception {
        String code = "move(<pos1>);";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidTimeDeclaration() throws Exception {
        String code = "Time t1 = 5;";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testInvalidMissingSemicolon() {
        String code = "Position startPos = (0,0,0)";
        Exception exception = assertThrows(RuntimeException.class, () -> parse(code));
        assertTrue(exception.getMessage().contains("syntax error"));
    }

    @Test
    void testInvalidToken() {
        String code = "Position startPos = (0,0,0); unknownToken";
        Exception exception = assertThrows(RuntimeException.class, () -> parse(code));
        assertTrue(exception.getMessage().contains("syntax error"));
    }
}
