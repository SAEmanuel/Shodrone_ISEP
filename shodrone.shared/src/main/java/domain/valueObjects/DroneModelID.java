package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public class DroneModelID implements ValueObject {

    private static final Pattern VALID_MODEL_ID_REGEX = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 50;

    /**
     * The model ID string of the DroneModelID.
     */
    final String modelID;

    /**
     * Constructs a DroneModelID with the specified model ID string.
     *
     * @param modelID the model ID string to set
     * @return void
     */
    public DroneModelID(final String modelID) {
        try {
            Preconditions.nonEmpty(modelID, "Model ID should neither be null nor empty");

            String modelIDTrimmed = modelID.trim();
            Preconditions.ensure(!modelIDTrimmed.isEmpty(), "Model ID cannot be only whitespace");
            Preconditions.ensure(modelIDTrimmed.length() >= MIN_LENGTH, "Model ID must have at least " + MIN_LENGTH + " characters");
            Preconditions.ensure(modelIDTrimmed.length() <= MAX_LENGTH, "Model ID must have at most " + MAX_LENGTH + " characters");
            Preconditions.matches(VALID_MODEL_ID_REGEX, modelIDTrimmed, "Invalid Model ID: " + modelIDTrimmed);

            this.modelID = modelIDTrimmed;

        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    /**
     * Default constructor required for JPA.
     *
     * @return void
     */
    protected DroneModelID() {
        modelID = null;
    }

    /**
     * Retrieves the model ID of the DroneModelID.
     *
     * @return the model ID as a String
     */
    public String getModelID() {
        return modelID;
    }

    /**
     * Returns the string representation of the DroneModelID.
     *
     * @return the model ID as a String
     */
    @Override
    public String toString() {
        return this.modelID;
    }

    /**
     * Checks if this DroneModelID is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DroneModelID)) return false;
        DroneModelID id = (DroneModelID) o;
        return modelID.equals(id.modelID);
    }

    /**
     * Computes the hash code of the DroneModelID.
     *
     * @return the hash code value for this DroneModelID
     */
    @Override
    public int hashCode() {
        return modelID.hashCode();
    }
}