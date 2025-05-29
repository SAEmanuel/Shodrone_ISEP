# US253 – Language Specification and Parsing for DroneOne DSL

## 1. Overview

A funcionalidade de especificação da linguagem de configuração e controle de drones permite que usuários definam instruções e propriedades específicas utilizando uma gramática ANTLR chamada **DroneOne.g4**. O parser gerado processa descrições textuais, assegurando conformidade com regras sintáticas que suportam variáveis de posição e vetor, tempos, chamadas de função e instruções específicas do domínio.

Essa funcionalidade fornece validação automática da entrada textual para drones, essencial para garantir a correção antes da interpretação ou simulação do comportamento.

---

## 2. 🧪 Tests

Os testes para **US253** focam na validação do parser gerado pela gramática **DroneOne.g4**. Eles verificam se a entrada textual é corretamente aceita ou rejeitada, assegurando que os elementos fundamentais da linguagem sejam reconhecidos conforme o esperado.

---

### 2.1. Parser Test: `DroneOneParserTest`

```java
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
        String code = "drone1.move(<pos1>);";
        ParseTree tree = parse(code);
        assertNotNull(tree);
    }

    @Test
    void testValidTimeDeclaration() throws Exception {
        String code = "Time t1 = 5 * 10 + 3;";
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
```

## 3. 🛠️ Construction (Implementation)
### 3.1. Parser Setup and Error Handling

A implementação do parser utiliza o código gerado pelo ANTLR a partir do arquivo DroneOne.g4. A validação ocorre ao tentar gerar a árvore sintática a partir da entrada fornecida.

```java
public class DroneOneParserFacade {

    private final DroneOneParser parser;

    public DroneOneParserFacade(String inputCode) {
        CharStream input = CharStreams.fromString(inputCode);
        DroneOneLexer lexer = new DroneOneLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        parser = new DroneOneParser(tokens);
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
public class DroneOneLanguageService {

    public boolean validateCode(String code) {
        try {
            DroneOneParserFacade parser = new DroneOneParserFacade(code);
            parser.parse();
            return true;
        } catch (RuntimeException e) {
            // Log or propagate error as needed
            return false;
        }
    }
}
```

## 4. 📦 Summary of Classes and Responsibilities

| Class/File              | Responsibility                                                                 |
|-------------------------|----------------------------------------------------------------------------------|
| `DroneOneLexer`         | Lexer gerado automaticamente pela ANTLR a partir de `DroneOne.g4`.             |
| `DroneOneParser`        | Parser gerado automaticamente pela ANTLR a partir de `DroneOne.g4`.            |
| `DroneOneParserFacade`  | Camada de fachada que encapsula parsing e tratamento de erros.                 |
| `DroneOneParserTest`    | Classe de testes JUnit que valida entradas válidas e inválidas da linguagem.   |
| `DroneOneLanguageService` | Serviço que expõe método de validação sintática para uso em camadas superiores.|

---

## 5. 🔗 Integration and Usage

- O parser gerado é utilizado em ferramentas de edição, compilação e validação de scripts de controle de drones.
- Os testes asseguram que a gramática esteja funcional e resistente a entradas malformadas.
- A arquitetura permite extensões futuras para **validação semântica** além da análise sintática.
- Mensagens de erro claras são fornecidas ao usuário para facilitar a **correção de problemas** com a linguagem.

---

## 6. 🔍 Observations

- A gramática ANTLR fornece um método **automatizado e formal** para definir a linguagem dos drones.
- A separação entre **análise sintática** (via ANTLR) e a **lógica de aplicação** garante modularidade e manutenibilidade.
- Os **testes automatizados** com JUnit asseguram a robustez da análise gramatical.
- A solução foi desenhada para futura integração com:
    - **Simuladores**
    - **Ambientes de desenvolvimento (IDEs)**
    - **Ferramentas de configuração de missões**
