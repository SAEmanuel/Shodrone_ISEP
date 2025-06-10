import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.*;

public class DSLFigureVisitor extends FiguresBaseVisitor<Object> {

    public DSLFigureModel model = new DSLFigureModel();
    private boolean onSegment = false;

    @Override
    public Object visitProgram(FiguresParser.ProgramContext ctx) {
        System.out.println();
        System.out.println();
        System.out.println("-------------------- START OF PROGRAM --------------------");
        System.out.println();

        if (ctx.versionDecl() != null) {
            visitVersionDecl(ctx.versionDecl());
        }

        for (var stmt : ctx.statement()) {
            visit(stmt);
        }

        System.out.println();
        System.out.println("-------------------- END OF PROGRAM --------------------");
        System.out.println();
        System.out.println();
        return model;
    }

    @Override
    public Object visitVersionDecl(FiguresParser.VersionDeclContext ctx) {
        String versionText = ctx.version_string().getText();
        model.version = versionText;
        System.out.println("Detected DSL version: " + versionText);
        System.out.println();
        return versionText;
    }

    @Override
    public Object visitDroneType(FiguresParser.DroneTypeContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        model.droneTypes.put(name, new DroneType(name));
        System.out.println("Defined drone type: " + name);
        System.out.println();
        return null;
    }

    @Override
    public Object visitVariable(FiguresParser.VariableContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        if (ctx.vector() != null) {
            List<Float> coords = extractVector(ctx.vector());
            model.positions.put(name, new Position(coords.get(0), coords.get(1), coords.get(2)));
            System.out.printf("Position %s = (%f, %f, %f)%n", name, coords.get(0), coords.get(1), coords.get(2));
        } else if (ctx.expr() != null) {
            double val = evalExpr(ctx.expr());
            if (ctx.getStart().getText().equals("Velocity")) {
                model.velocities.put(name, new Velocity(val));
                System.out.printf("Velocity %s = %f%n", name, val);
            } else if (ctx.getStart().getText().equals("Distance")) {
                model.distances.put(name, new Distance(val));
                System.out.printf("Distance %s = %f%n", name, val);
            }
        }
        return null;
    }

    @Override
    public Object visitInstantiation(FiguresParser.InstantiationContext ctx) {
        String type = ctx.TYPE().getText();
        String name = ctx.IDENTIFIER().getText();
        List<String> args = collectArgs(ctx.argList());

        model.shapes.put(name, new Shape(name, type, args));
        System.out.printf("Instantiation: %s %s(%s)%n", type, name, String.join(", ", args));
        return null;
    }

    @Override
    public Object visitSegment(FiguresParser.SegmentContext ctx) {
        System.out.println();
        onSegment = true;
        MovementBlock.Type type;
        String keyword = ctx.getStart().getText();
        if (keyword.equals("before")) type = MovementBlock.Type.BEFORE;
        else if (keyword.equals("after")) type = MovementBlock.Type.AFTER;
        else type = MovementBlock.Type.GROUP;

        MovementBlock block = new MovementBlock(type);
        //System.out.println(">>> Entering segment: " + type);
        System.out.println(type);

        for (var stmt : ctx.segmentBody().statement()) {
            if (stmt.variable() != null) {
                visitVariable(stmt.variable());
                String varType = stmt.variable().getStart().getText();
                String name = stmt.variable().IDENTIFIER().getText();
                if (stmt.variable().vector() != null) {
                    List<Float> coords = extractVector(stmt.variable().vector());
                    System.out.printf("  %s %s = (%f, %f, %f)%n", varType, name, coords.get(0), coords.get(1), coords.get(2));
                } else if (stmt.variable().expr() != null) {
                    double val = evalExpr(stmt.variable().expr());
                    System.out.printf("  %s %s = %f%n", varType, name, val);
                }
            } else if (stmt.functionCall() != null) {
                Command cmd = (Command) visitFunctionCall(stmt.functionCall());
                block.commands.add(cmd);
                System.out.printf("  %s.%s(%s)%n", cmd.target(), cmd.action(), String.join(", ", cmd.args()));
            } else if (stmt.pauseStmt() != null) {
                Command pause = (Command) visitPauseStmt(stmt.pauseStmt());
                block.commands.add(pause);
                System.out.printf("  pause(%s);%n", pause.args().get(0));
            } else if (stmt.instantiation() != null) {
                visitInstantiation(stmt.instantiation());
                String instType = stmt.instantiation().TYPE().getText();
                String instName = stmt.instantiation().IDENTIFIER().getText();
                List<String> args = collectArgs(stmt.instantiation().argList());
                System.out.printf("  %s %s(%s)%n", instType, instName, String.join(", ", args));
            } else if (stmt.segment() != null) {
                visitSegment(stmt.segment());  // Recursive visit
                String innerType = stmt.segment().getStart().getText();
                System.out.println(innerType);
            }
        }

        model.blocks.add(block);
        //System.out.println("<<< Exiting segment: " + type);
        System.out.println(type);
        onSegment = false;
        return null;
    }

    @Override
    public Object visitFunctionCall(FiguresParser.FunctionCallContext ctx) {
        String target = ctx.IDENTIFIER(0).getText();
        String action = ctx.IDENTIFIER(1).getText();
        List<String> args = collectArgs(ctx.argList());

        if(onSegment == false)
            System.out.printf("%s.%s(%s)%n", target, action, String.join(", ", args));
        return new Command(target, action, args);
    }

    @Override
    public Object visitPauseStmt(FiguresParser.PauseStmtContext ctx) {
        System.out.println();
        String seconds = ctx.INT().getText();
        try {
            System.out.println();
            String seconds = ctx.INT().getText();

        System.out.printf("pause(%s);%n", seconds);
        return new Command("system", "pause", List.of(seconds));
    }


    // ------------------ Helpers ------------------

    private List<String> collectArgs(FiguresParser.ArgListContext ctx) {
        List<String> args = new ArrayList<>();
        while (ctx != null) {
            args.add(ctx.arguement().getText());
            ctx = ctx.argList();
        }
        return args;
    }

    private List<Float> extractVector(FiguresParser.VectorContext ctx) {
        float x = Float.parseFloat(ctx.number(0).getText());
        float y = Float.parseFloat(ctx.number(1).getText());
        float z = Float.parseFloat(ctx.number(2).getText());
        return List.of(x, y, z);
    }

    private double evalExpr(FiguresParser.ExprContext ctx) {
        if (ctx.literal() != null) {
            return evalLiteral(ctx.literal());
        }
        double left = evalLiteral(ctx.literal());
        double right = evalExpr(ctx.expr());
        return switch (ctx.op.getText()) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> throw new RuntimeException("Unknown operator: " + ctx.op.getText());
        };
    }

    private double evalLiteral(FiguresParser.LiteralContext ctx) {
        if (ctx.CONSTANT() != null) {
            return switch (ctx.CONSTANT().getText()) {
                case "PI" -> Math.PI;
                case "e" -> Math.E;
                default -> throw new RuntimeException("Unknown constant: " + ctx.CONSTANT().getText());
            };
        } else if (ctx.FLOAT() != null) {
            return Double.parseDouble(ctx.FLOAT().getText());
        } else if (ctx.INT() != null) {
            return Double.parseDouble(ctx.INT().getText());
        }
        throw new RuntimeException("Invalid literal: " + ctx.getText());
    }

    // ------------------ Model ------------------

    public static class DSLFigureModel {
        public String version;
        public Map<String, DroneType> droneTypes = new HashMap<>();
        public Map<String, Position> positions = new HashMap<>();
        public Map<String, Velocity> velocities = new HashMap<>();
        public Map<String, Distance> distances = new HashMap<>();
        public Map<String, Shape> shapes = new HashMap<>();
        public List<MovementBlock> blocks = new ArrayList<>();
    }

    public record DroneType(String name) {}
    public record Position(float x, float y, float z) {}
    public record Velocity(double value) {}
    public record Distance(double value) {}
    public record Shape(String name, String type, List<String> constructorArgs) {}

    public static class MovementBlock {
        public enum Type { BEFORE, GROUP, AFTER }
        public Type type;
        public List<Command> commands = new ArrayList<>();
        public MovementBlock(Type type) { this.type = type; }
        public MovementBlock(Type type, List<Command> cmds) {
            this.type = type;
            this.commands = cmds;
        }
    }
