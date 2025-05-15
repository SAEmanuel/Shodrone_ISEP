import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromFileName("input.txt");
        DroneOneLexer lexer = new DroneOneLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DroneOneParser parser = new DroneOneParser(tokens);
        ParseTree tree = parser.program(); // replace with your start rule

        System.out.println(tree.toStringTree(parser));
    }
}
