package drone_language_validation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Enum that defines the required fields used in the DroneGeneric DSL specification.
 * It includes mandatory types and instructions that must be present or validated in a program.
 */
public enum RequiredFields {

    /**
     * Required type definitions for the DSL.
     * These include fundamental data types like Vector, Point, and Time.
     */
    TYPES(Arrays.asList("Point", "Position", "Vector", "LinearVelocity", "AngularVelocity", "Distance", "Time")),

    /**
     * Required instruction names supported by the DSL.
     * These represent predefined drone commands.
     */
    INSTRUCTIONS(Arrays.asList("takeOff", "move", "movePath", "moveCircle", "hoover", "lightsOn", "lightsOff", "land"));

    /**
     * List of valid field names associated with the enum entry.
     */
    private final List<String> fieldNames;

    /**
     * Constructs a RequiredFields enum value with a list of field names.
     *
     * @param fieldNames List of required type or instruction names
     */
    RequiredFields(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    /**
     * Returns the list of required field names associated with this enum value.
     *
     * @return List of field names
     */
    public List<String> getFieldNames() {
        return fieldNames;
    }

    /**
     * Checks whether a given field name is included in the list of required fields.
     *
     * @param fieldName The name of the type or instruction to check
     * @return true if the field is required, false otherwise
     */
    public boolean contains(String fieldName) {
        return fieldNames.contains(fieldName);
    }

    /**
     * Retrieves the RequiredFields enum instance (TYPES or INSTRUCTIONS) that contains the given field name.
     *
     * @param fieldName Name to search for
     * @return Optional containing the matching RequiredFields instance, or empty if not found
     */
    public static Optional<RequiredFields> getByFieldName(String fieldName) {
        return Arrays.stream(values()).filter(rf -> rf.contains(fieldName)).findFirst();
    }
}
