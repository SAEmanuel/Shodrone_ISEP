package drone_language_validation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum RequiredFields {

    TYPES(Arrays.asList("Point","Poisition", "Vector", "LinearVelocity", "AngularVelocity", "Distance", "Time")),
    INSTRUCTIONS(Arrays.asList("takeOff", "move", "movePath", "moveCircle","hoover","lightsOn","lightsOff","land"));


    private final List<String> fieldNames;

    RequiredFields(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public boolean contains(String fieldName) {
        return fieldNames.contains(fieldName);
    }


    public static Optional<RequiredFields> getByFieldName(String fieldName) {
        return Arrays.stream(values()).filter(rf -> rf.contains(fieldName)).findFirst();
    }
}
