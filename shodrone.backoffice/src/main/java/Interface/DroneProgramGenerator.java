package Interface;

import domain.entity.DroneModel;
import domain.entity.Figure;
import figure_dsl.validator.FigureValidationPlugin;
import utils.DslMetadata;

import java.util.List;
import java.util.Map;

public interface DroneProgramGenerator {

    public String generateProgram(Figure figure, DroneModel droneModel, String dslVersion,
                                  DslMetadata dslMetadata, FigureValidationPlugin validator,
                                  int droneId, Map<Integer, List<int[]>> dronePositions);
}
