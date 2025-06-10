package controller.drone;

import Interface.DroneProgramGenerator;
import drone.DroneProgramGeneratorVisitor;
import drone_code.generated.DroneShowDSLLexer;
import drone_code.generated.DroneShowDSLParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class DroneOneProgramGeneratorController implements DroneProgramGenerator {

    @Override
    public void generateDronePrograms(String dslContent) {
        try {
            CharStream input = CharStreams.fromString(dslContent);
            DroneShowDSLLexer lexer = new DroneShowDSLLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            DroneShowDSLParser parser = new DroneShowDSLParser(tokens);
            ParseTree tree = parser.dslFile();

            DroneProgramGeneratorVisitor visitor = new DroneProgramGeneratorVisitor();
            visitor.visit(tree);

            System.out.println("Drone code generated successfully.");

            visitor.exportDronePositionFiles("docs/planning/sprint3/SCOMP/project/scripts");

        } catch (Exception e) {
            System.err.println("Error parsing DSL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
