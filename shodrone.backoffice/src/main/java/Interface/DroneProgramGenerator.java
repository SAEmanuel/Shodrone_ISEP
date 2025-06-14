package Interface;

import domain.entity.DroneModel;
import domain.entity.Figure;
import utils.DslMetadata;

public interface DroneProgramGenerator {

    String generateProgram(Figure figure, DroneModel droneModel, String dslVersion, DslMetadata dslMetadata);
}
