package drone;

import Interface.DroneProgramGenerator;
import domain.entity.DroneModel;
import domain.entity.Figure;
import utils.DslMetadata;

public class DroneProgramsGenerator implements DroneProgramGenerator {

    @Override
    public String generateProgram(Figure figure, DroneModel droneModel, String dslVersion, DslMetadata dslMetadata) {
        StringBuilder sb = new StringBuilder();

        sb.append("Drone programming language version ").append(dslVersion).append("\n\n");

        sb.append("// Generating program for Figure: ").append(figure.name()).append("\n");
        sb.append("// Drone Model: ").append(droneModel.identity()).append("\n");
        sb.append("// DSL Version: ").append(dslVersion).append("\n\n");

        for (String line : dslMetadata.getDslLines()) {
            sb.append(line).append("\n");
        }

        return sb.toString();
    }
}