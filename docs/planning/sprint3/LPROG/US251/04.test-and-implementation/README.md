# US251 - Language Specification and Parsing for Figures DSL

## 1. Overview

A funcionalidade de especificação da linguagem de descrição de figuras permite que usuários definam e validem figuras e comandos para drones utilizando uma gramática ANTLR chamada **Figures.g4**. O parser gerado processa descrições textuais, assegurando conformidade com regras sintáticas que suportam tipos de drones, variáveis, instâncias, segmentos, comandos de pausa e chamadas de função.

Esta funcionalidade suporta a validação automática da linguagem, essencial para garantir a correta construção das descrições antes da sua interpretação ou execução.

---

## 2. Tests

Os testes para **US251** focam na validação do parser gerado pela gramática **Figures.g4**. Eles verificam se a entrada textual é corretamente aceita ou rejeitada, garantindo que os principais elementos da linguagem estejam corretamente reconhecidos.

---

### 2.1. Parser Test: `FiguresParserTest`

```java
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FiguresParserTest {

    private ParseTree parse(String code) throws Exception {
        CharStream input = CharStreams.fromString(code);
        FiguresLexer lexer = new FiguresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FiguresParser parser = new FiguresParser(tokens);
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
    void testValidDroneTypeDeclaration() throws Exception {
        String code = "DroneType MyDrone;";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidVariablePositionDeclaration() throws Exception {
        String code = "Position startPos = (0,0,0);";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidInstantiation() throws Exception {
        String code = "DroneType drone1();";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidSegmentWithStatements() throws Exception {
        String code = "before\n" +
                      "Position pos1 = (1,2,3);\n" +
                      "pause(5);\n" +
                      "endbefore";
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
    void testFunctionCallWithArguments() throws Exception {
        String code = "drone1.move((1,2,3));";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }
}
```

## 3. Construction (Implementation)

### 3.1. Parser Setup and Error Handling

A implementação do parser utiliza o código gerado pelo ANTLR a partir do arquivo Figures.g4. A validação ocorre ao tentar gerar a árvore sintática a partir da entrada fornecida.

```java
public class FiguresParserFacade {

    private final FiguresParser parser;

    public FiguresParserFacade(String inputCode) {
        CharStream input = CharStreams.fromString(inputCode);
        FiguresLexer lexer = new FiguresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new FiguresParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                throw new RuntimeException("Syntax error at line " + line + ":" + charPositionInLine + " - " + msg);
            }
        });
    }

    /**
     * Parses the entire program input and returns the parse tree.
     * Throws RuntimeException if syntax errors are detected.
     */
    public ParseTree parse() {
        return parser.program();
    }
}
```

### 3.2. Usage Example

```java
public class FiguresLanguageService {

    public boolean validateCode(String code) {
        try {
            FiguresParserFacade parser = new FiguresParserFacade(code);
            parser.parse();
            return true;
        } catch (RuntimeException e) {
            // Log or propagate error as needed
            return false;
        }
    }
}
```

## 4. Summary of Classes and Responsibilities

- **FiguresLexer:** Lexer generated by ANTLR from Figures.g4.
- **FiguresParser:** Parser generated by ANTLR from Figures.g4.
- **FiguresParserFacade:** Wrapper around the parser to manage error handling and parsing interface.
- **FiguresParserTest:** JUnit test class verifying correct parsing of valid and invalid DSL snippets.
- **FiguresLanguageService:** Service class exposing validation method for input code.

## 5. Integration and Usage

- The generated parser is used in editing, compiling, and validation tools to ensure figure descriptions are correct before execution.
- Tests guarantee parser reliability for all elements defined in the grammar.
- The architecture allows easy extension to include semantic validation beyond syntax.
- Clear error messages are provided to users to facilitate DSL code correction.

## 6. Observations

- The ANTLR grammar offers a formal and automated way to specify the figures language.
- Clear separation between syntax analysis (ANTLR) and application logic.
- Automated tests ensure robustness in accepting valid inputs and rejecting invalid ones.
- Design enables easy integration with IDEs and user support tools.


