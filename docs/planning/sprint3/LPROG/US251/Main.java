import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromFileName("input.txt");
        FiguresLexer lexer = new FiguresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FiguresParser parser = new FiguresParser(tokens);
        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));
    }
}
