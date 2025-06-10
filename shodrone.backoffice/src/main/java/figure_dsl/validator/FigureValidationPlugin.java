package figure_dsl.validator;

import figure_dsl.generated.dslParser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class FigureValidationPlugin {
    @Setter
    private String droneModelName;
    private int droneModelCount;
    private final List<String> errors;
    private final Map<String, Map<String, String>> declaredVariables;

    public FigureValidationPlugin() {
        this.droneModelName = null;
        this.droneModelCount = 0;
        this.errors = new ArrayList<>();
        this.declaredVariables = new HashMap<>();
        this.declaredVariables.put("DroneType", new HashMap<>());
        this.declaredVariables.put("Position", new HashMap<>());
        this.declaredVariables.put("Velocity", new HashMap<>());
        this.declaredVariables.put("Distance", new HashMap<>());
    }

    public void validate(dslParser.DslContext parseTree) {
        resetState();

        DroneModelValidator droneValidator = new DroneModelValidator(this);
        droneValidator.visit(parseTree);

        DeclaredVariablesValidator varValidator = new DeclaredVariablesValidator(this);
        varValidator.visit(parseTree);

        StatementBlockValidator stmtValidator = new StatementBlockValidator(this);
        stmtValidator.visit(parseTree);

        validateDroneModelPresence();
    }


    public boolean isValid() {
        return errors.isEmpty();
    }

    void addDroneModel(String modelName) {
        declaredVariables.get("DroneType").put(modelName, "");
    }

    private void resetState() {
        droneModelName = null;
        droneModelCount = 0;
        errors.clear();
        declaredVariables.forEach((k, v) -> v.clear());
    }

    private void validateDroneModelPresence() {
        if (droneModelCount == 0) {
            errors.add("Error: No 'DroneType' declaration found. Exactly one is required.");
        }
    }

    void incrementDroneModelCount() {
        droneModelCount++;
    }

    void addError(String error) {
        errors.add(error);
    }
}
