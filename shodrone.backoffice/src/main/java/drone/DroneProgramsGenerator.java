package drone;

import Interface.DroneProgramGenerator;
import domain.entity.DroneModel;
import domain.entity.Figure;
import figure_dsl.validator.FigureValidationPlugin;
import utils.DslMetadata;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DroneProgramsGenerator implements DroneProgramGenerator {

    @Override
    public String generateProgram(Figure figure, DroneModel droneModel, String dslVersion,
                                  DslMetadata dslMetadata, FigureValidationPlugin validator,
                                  int droneId, Map<Integer, List<int[]>> dronePositions) {

        StringBuilder sb = new StringBuilder();

        sb.append(droneModel.identity()).append(" programming language version ").append(dslVersion).append("\n\n");

        sb.append("Types\n\n");
        sb.append("Position\n");
        sb.append("Vector\n");
        sb.append("LinearVelocity\n");
        sb.append("AngularVelocity\n");
        sb.append("Distance\n");
        sb.append("Time\n\n");

        sb.append("Variables\n\n");
        validator.getDeclaredVariables().forEach((type, vars) -> {
            for (Map.Entry<String, String> entry : vars.entrySet()) {
                String droneOneType = switch (type) {
                    case "Position" -> "Position";
                    case "Velocity" -> "LinearVelocity";
                    case "Distance" -> "Distance";
                    case "DroneType" -> null;
                    default -> type;
                };
                if (droneOneType != null && !entry.getValue().isEmpty()) {
                    sb.append(droneOneType).append(" ").append(entry.getKey()).append(" = ").append(entry.getValue()).append(";\n");
                }
            }
        });

        sb.append("\nPosition arrayOfPositions = (");
        List<String> shapeLines = dslMetadata.getDslLines().stream()
                .filter(line -> line.matches("^(Line|Rectangle|Circle|Circumference|Square|Spiral).*"))
                .toList();

        dronePositions.putIfAbsent(droneId, new ArrayList<>());
        List<int[]> positions = dronePositions.get(droneId);

        boolean filledArray = false;

        if (!shapeLines.isEmpty()) {
            try {
                String shapeLine = shapeLines.get(0);
                Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(shapeLine);
                if (matcher.find()) {
                    String[] args = matcher.group(1).split(",");
                    String posVar = args[0].trim().replaceAll("[^a-zA-Z0-9_]", "");
                    String distVar1 = args[1].trim().split("[+\\-*/]")[0].trim();
                    String distVar2 = args.length > 2 ? args[2].trim().split("[+\\-*/]")[0].trim() : distVar1;

                    String posStr = validator.getDeclaredVariables().getOrDefault("Position", Map.of()).get(posVar);
                    String distStr1 = validator.getDeclaredVariables().getOrDefault("Distance", Map.of()).get(distVar1);
                    String distStr2 = validator.getDeclaredVariables().getOrDefault("Distance", Map.of()).get(distVar2);

                    if (posStr != null && distStr1 != null && distStr2 != null) {
                        String[] coords = posStr.replace("(", "").replace(")", "").split(",");
                        double x = Double.parseDouble(coords[0].trim());
                        double y = Double.parseDouble(coords[1].trim());
                        double z = Double.parseDouble(coords[2].trim());
                        double dx = Double.parseDouble(distStr1.trim());
                        double dy = Double.parseDouble(distStr2.trim());

                        List<String> points = new ArrayList<>();
                        switch (shapeLine.split(" ")[0]) {
                            case "Line" -> {
                                int ix = (int) x, iy = (int) y, iz = (int) z, iz2 = (int) (z + dx);
                                points.add("(" + ix + "," + iy + "," + iz + ")");
                                points.add("(" + ix + "," + iy + "," + iz2 + ")");
                                positions.add(new int[]{ix, iy, iz});
                                positions.add(new int[]{ix, iy, iz2});
                            }
                            case "Rectangle", "Square" -> {
                                int ix = (int) x, iy = (int) y, iz = (int) z;
                                int iydx = (int) (y + dx), izdy = (int) (z + dy);
                                points.add("(" + ix + "," + iy + "," + iz + ")");
                                positions.add(new int[]{ix, iy, iz});
                                points.add("(" + ix + "," + iydx + "," + iz + ")");
                                positions.add(new int[]{ix, iydx, iz});
                                points.add("(" + ix + "," + iydx + "," + izdy + ")");
                                positions.add(new int[]{ix, iydx, izdy});
                                points.add("(" + ix + "," + iy + "," + izdy + ")");
                                positions.add(new int[]{ix, iy, izdy});
                            }
                            case "Circle", "Circumference" -> {
                                for (int i = 0; i < 8; i++) {
                                    double angle = i * Math.PI / 4;
                                    int px = (int) Math.round(x + dx * Math.cos(angle));
                                    int py = (int) Math.round(y + dx * Math.sin(angle));
                                    int pz = (int) Math.round(z);
                                    points.add("(" + px + "," + py + "," + pz + ")");
                                    positions.add(new int[]{px, py, pz});
                                }
                            }
                            case "Spiral" -> {
                                for (int i = 0; i < 10; i++) {
                                    double angle = i * Math.PI / 4;
                                    int px = (int) Math.round(x + dx * Math.cos(angle));
                                    int py = (int) Math.round(y + dx * Math.sin(angle));
                                    int pz = (int) Math.round(z + i);
                                    points.add("(" + px + "," + py + "," + pz + ")");
                                    positions.add(new int[]{px, py, pz});
                                }
                            }
                        }
                        sb.append(String.join(", ", points)).append(");\n");
                        filledArray = true;
                    }
                }
            } catch (Exception e) {
            }
        }

        if (!filledArray) {
            Random rand = new Random();
            List<String> randomPoints = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int x = rand.nextInt(51);
                int y = rand.nextInt(51);
                int z = rand.nextInt(51);
                randomPoints.add("(" + x + "," + y + "," + z + ")");
                positions.add(new int[]{x, y, z});
            }
            sb.append(String.join(", ", randomPoints)).append(");\n");
        }

        Random rand = new Random();
        String height = String.valueOf(rand.nextInt(10) + 1);
        String velocity = validator.getDeclaredVariables().getOrDefault("Velocity", Map.of())
                .keySet().stream().findFirst().orElse("v");
        String duration = String.valueOf(rand.nextInt(20) + 10);
        String time = String.valueOf(rand.nextInt(61));
        String period = String.valueOf(rand.nextInt(61));

        sb.append("\nInstructions\n\n");
        sb.append("takeOff(" + height + ", " + velocity + ");\n");
        sb.append("land(" + velocity + ");\n");
        sb.append("move((x, y, z), " + velocity + ");\n");
        sb.append("move((dx, dy, dz), " + velocity + ", " + duration + ");\n");
        sb.append("movePath(arrayOfPositions, " + velocity + ");\n");
        sb.append("hoover(" + time + ");\n\n");
        sb.append("lightsOn();\n");
        sb.append("lightsOff();\n");
        sb.append("blink(" + period + ");\n\n");

        return sb.toString();
    }
}
