package figure_dsl.validator;

import figure_dsl.generated.dslBaseVisitor;
import figure_dsl.generated.dslParser.Drone_modelContext;

public class DroneModelValidator extends dslBaseVisitor<Void> {
    private final FigureValidationPlugin validator;

    public DroneModelValidator(FigureValidationPlugin validator) {
        this.validator = validator;
    }

    @Override
    public Void visitDrone_model(Drone_modelContext ctx) {
        validator.incrementDroneModelCount();

        if (validator.getDroneModelCount() == 1) {
            String modelName = ctx.ID().getText();
            validator.setDroneModelName(modelName);
            validator.addDroneModel(modelName);
        } else {
            validator.addError("Error at line " + ctx.getStart().getLine() +
                    ": Multiple drone model declarations found. Only one 'DroneType' is allowed.");
        }
        return null;
    }
}
