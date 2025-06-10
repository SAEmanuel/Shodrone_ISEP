import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        // Check for input file
        if (args.length < 1) {
            System.err.println("Usage: java Main <input.dsl>");
            System.exit(1);
        }

        String inputFile = args[0];
        String source = Files.readString(Paths.get(inputFile));

        // Create input stream from the DSL file
        CharStream input = CharStreams.fromString(source);

        // Create lexer and parser
        FiguresLexer lexer = new FiguresLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FiguresParser parser = new FiguresParser(tokens);

        // Optional: remove default error listeners and add custom one
        parser.removeErrorListeners();
        parser.addErrorListener(new DiagnosticErrorListener());

        // Parse the input starting from the root rule
        ParseTree tree = parser.program();

        // Visit and extract model
        DSLFigureVisitor visitor = new DSLFigureVisitor();
        DSLFigureVisitor.DSLFigureModel model = (DSLFigureVisitor.DSLFigureModel) visitor.visit(tree);

        /*
        // Output the extracted model (basic summary)
        System.out.println("Drone Types: " + model.droneTypes.keySet());
        System.out.println("Positions: " + model.positions.keySet());
        System.out.println("Shapes: " + model.shapes.keySet());
        System.out.println("Blocks: " + model.blocks.size());*/
    }
}
