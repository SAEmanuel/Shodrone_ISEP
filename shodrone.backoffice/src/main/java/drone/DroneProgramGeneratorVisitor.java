package drone;

import drone_code.DroneShowDSLBaseVisitor;
import drone_code.DroneShowDSLParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DroneProgramGeneratorVisitor extends DroneShowDSLBaseVisitor<Void> {

    private final Map<String, List<String>> dronePrograms = new HashMap<>();
    private final Map<String, Double> velocities = new HashMap<>();
    private final Map<String, int[]> positions = new HashMap<>();
    private final Map<String, List<String>> dronePositions = new HashMap<>();

    public Map<String, List<String>> getDronePrograms() {
        return dronePrograms;
    }

    @Override
    public Void visitDslFile(DroneShowDSLParser.DslFileContext ctx) {
        System.out.println("Parsing DSL...");
        return super.visitDslFile(ctx);
    }

    @Override
    public Void visitPositionDecl(DroneShowDSLParser.PositionDeclContext ctx) {
        String id = ctx.ID().getText();
        List<Double> coords = evaluateVector(ctx.vector());
        int[] pos = coords.stream().mapToInt(Double::intValue).toArray();
        positions.put(id, pos);
        return null;
    }

    @Override
    public Void visitVelocityDecl(DroneShowDSLParser.VelocityDeclContext ctx) {
        String id = ctx.ID().getText();
        double value = evaluateExpr(ctx.expr());
        velocities.put(id, value);
        return null;
    }

    @Override
    public Void visitCommand(DroneShowDSLParser.CommandContext ctx) {
        String shapeID = ctx.ID(0).getText();
        String commandName = ctx.ID(1).getText();

        List<String> args = new ArrayList<>();
        if (ctx.exprArguments() != null) {
            for (var expr : ctx.exprArguments().expr()) {
                args.add(expr.getText());
            }
        }

        List<String> droneIDs = dronePrograms.keySet().stream()
                .filter(id -> id.startsWith(shapeID + "_"))
                .toList();

        for (String droneID : droneIDs) {
            String fullCommand = droneID + "." + commandName +
                    (args.isEmpty() ? "();" : "(" + String.join(", ", args) + ");");

            dronePrograms.computeIfAbsent(droneID, k -> new ArrayList<>()).add(fullCommand);

            if (commandName.equals("move")) {
                List<DroneShowDSLParser.ExprContext> moveArgs = ctx.exprArguments().expr();
                DroneShowDSLParser.ExprContext dirExpr = moveArgs.get(0);
                List<Double> dirVec = null;

                if (dirExpr.atom() != null && dirExpr.atom().vector() != null) {
                    dirVec = evaluateVector(dirExpr.atom().vector());
                } else if (dirExpr.atom() != null && dirExpr.atom().ID() != null) {
                    String dirID = dirExpr.atom().ID().getText();
                    if (positions.containsKey(dirID)) {
                        int[] vec = positions.get(dirID);
                        dirVec = Arrays.stream(vec).asDoubleStream().boxed().toList();
                    } else {
                        System.err.println("Unknown vector ID " + dirID);
                        continue;
                    }
                } else {
                    System.err.println("Unsupported direction expression for drone " + droneID);
                    continue;
                }

                double distance = evaluateExpr(moveArgs.get(1));
                double velocity = evaluateExpr(moveArgs.get(2));

                int steps = (int) Math.max(1, Math.ceil(distance / velocity));
                int[] dir = dirVec.stream().mapToInt(Double::intValue).toArray();

                int[] current = Arrays.copyOf(positions.get(droneID), 3);
                dronePositions.computeIfAbsent(droneID, k -> new ArrayList<>()).add(formatPosition(current));

                for (int i = 0; i < steps; i++) {
                    for (int j = 0; j < 3; j++) {
                        current[j] += dir[j];
                    }
                    dronePositions.get(droneID).add(formatPosition(current));
                }

                positions.put(droneID, current);

            } else if (commandName.equals("rotate")) {
                List<DroneShowDSLParser.ExprContext> rotateArgs = ctx.exprArguments().expr();
                DroneShowDSLParser.ExprContext centerExpr = rotateArgs.get(0);
                List<Double> centerVec = null;

                if (centerExpr.atom() != null && centerExpr.atom().vector() != null) {
                    centerVec = evaluateVector(centerExpr.atom().vector());
                } else if (centerExpr.atom() != null && centerExpr.atom().ID() != null) {
                    String centerID = centerExpr.atom().ID().getText();
                    if (positions.containsKey(centerID)) {
                        int[] vec = positions.get(centerID);
                        centerVec = Arrays.stream(vec).asDoubleStream().boxed().toList();
                    } else {
                        System.err.println("Unknown vector ID: " + centerID);
                        continue;
                    }
                } else {
                    System.err.println("Unsupported center expression for drone " + droneID);
                    continue;
                }

                double cx = centerVec.get(0);
                double cy = centerVec.get(1);
                double cz = centerVec.get(2);

                double angle = evaluateExpr(rotateArgs.get(2));
                double angularVelocity = evaluateExpr(rotateArgs.get(3));
                double duration = Math.abs(angle) / angularVelocity;
                int steps = (int) Math.max(1, Math.ceil(duration));

                int[] current = Arrays.copyOf(positions.get(droneID), 3);
                double px = current[0];
                double py = current[1];
                double dx = px - cx;
                double dy = py - cy;
                double radius = Math.sqrt(dx * dx + dy * dy);
                double initialAngle = Math.atan2(dy, dx);

                dronePositions.computeIfAbsent(droneID, k -> new ArrayList<>()).add(formatPosition(current));

                for (int i = 1; i <= steps; i++) {
                    double theta = initialAngle + (angle / steps) * i;
                    int newX = (int) Math.round(cx + radius * Math.cos(theta));
                    int newY = (int) Math.round(cy + radius * Math.sin(theta));
                    int newZ = (int) Math.round(cz);
                    current[0] = newX;
                    current[1] = newY;
                    current[2] = newZ;
                    dronePositions.get(droneID).add(formatPosition(current));
                }

                positions.put(droneID, current);
            }
        }
        return null;
    }

    private double evaluateExpr(DroneShowDSLParser.ExprContext ctx) {
        if (ctx.getChildCount() == 1) return evaluateAtom(ctx.atom());
        if (ctx.getChildCount() == 2) return -evaluateExpr(ctx.expr(0));
        if (ctx.getChildCount() == 3) {
            double left = evaluateExpr(ctx.expr(0));
            double right = evaluateExpr(ctx.expr(1));
            return switch (ctx.getChild(1).getText()) {
                case "+" -> left + right;
                case "-" -> left - right;
                case "*" -> left * right;
                case "/" -> left / right;
                default -> throw new IllegalArgumentException("Unknown operator");
            };
        }
        return 0;
    }

    private List<Double> evaluateVector(DroneShowDSLParser.VectorContext ctx) {
        List<Double> v = new ArrayList<>();
        for (var expr : ctx.expr()) v.add(evaluateExpr(expr));
        return v;
    }

    private String formatPosition(int[] pos) {
        return pos[0] + " " + pos[1] + " " + pos[2];
    }

    private double evaluateAtom(DroneShowDSLParser.AtomContext ctx) {
        if (ctx.NUMBER() != null) return Double.parseDouble(ctx.NUMBER().getText());
        if (ctx.getText().equals("PI")) return Math.PI;
        if (ctx.getText().startsWith("PI/")) return Math.PI / Double.parseDouble(ctx.getText().split("/")[1]);
        if (ctx.ID() != null && velocities.containsKey(ctx.ID().getText())) return velocities.get(ctx.ID().getText());
        return 0;
    }

    public void exportDronePositionFiles(String outputDirPath) {
        try {
            Files.createDirectories(Paths.get(outputDirPath));
            String fileName = outputDirPath + "/script_" + dronePositions.size() + "_drones.txt";
            Path file = Paths.get(fileName);

            Map<String, Integer> droneIndex = new HashMap<>();
            int idx = 0;
            for (String droneID : dronePositions.keySet()) {
                droneIndex.put(droneID, idx++);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                for (var entry : dronePositions.entrySet()) {
                    String droneID = entry.getKey();
                    int id = droneIndex.get(droneID);
                    writer.write(id + " - 3x3x3\n");
                    for (String pos : entry.getValue()) {
                        writer.write(pos + "\n");
                    }
                }
            }
            System.out.println("Drone position file exported: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void visitShapeDecl(DroneShowDSLParser.ShapeDeclContext ctx) {
        String shapeID = ctx.ID().getText();
        String shapeType = ctx.getChild(0).getText();

        List<DroneShowDSLParser.ExprContext> exprs = ctx.exprList().expr();

        int numDrones;
        switch (shapeType.toLowerCase()) {
            case "line":
                numDrones = 5;
                break;
            case "rectangle":
                numDrones = 4;
                break;
            default:
                numDrones = 3;
        }

        DroneShowDSLParser.ExprContext firstExpr = exprs.get(0);
        int[] basePosition;

        if (firstExpr.atom() != null && firstExpr.atom().vector() != null) {
            List<Double> coords = evaluateVector(firstExpr.atom().vector());
            basePosition = coords.stream().mapToInt(Double::intValue).toArray();
        } else if (firstExpr.atom() != null && firstExpr.atom().ID() != null) {
            String posID = firstExpr.atom().ID().getText();
            basePosition = positions.getOrDefault(posID, new int[]{0, 0, 0});
        } else {
            System.err.println("Invalid position in shape: " + shapeID);
            basePosition = new int[]{0, 0, 0};
        }

        for (int i = 0; i < numDrones; i++) {
            String droneID = shapeID + "_" + i;
            int[] pos = Arrays.copyOf(basePosition, 3);
            pos[0] += i;
            positions.put(droneID, pos);

            dronePositions.computeIfAbsent(droneID, k -> new ArrayList<>()).add(formatPosition(pos));

            dronePrograms.putIfAbsent(droneID, new ArrayList<>());
        }

        return null;
    }

}
