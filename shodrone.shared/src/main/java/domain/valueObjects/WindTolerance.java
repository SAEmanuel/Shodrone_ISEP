package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class WindTolerance implements ValueObject {

    private final Integer minWindSpeed;
    private final Integer maxWindSpeed;
    private final Integer xTolerance;
    private final Integer yTolerance;
    private final Integer zTolerance;

    public WindTolerance(final Integer minWindSpeed, final Integer maxWindSpeed, final Integer xTolerance, final Integer yTolerance, final Integer zTolerance, final Integer maxDroneWindResistance) {

        try {
            Preconditions.ensure(maxDroneWindResistance > maxWindSpeed, "Error: The wind range (up to " + maxWindSpeed + " m/s) exceeds the maximum limit of this model (" + maxDroneWindResistance + " m/s).");

            Preconditions.nonNull(minWindSpeed, "Minimum Wind Speed cannot be null");
            Preconditions.nonNull(maxWindSpeed, "Maximum Wind Speed cannot be null");
            Preconditions.nonNull(xTolerance, "X Tolerance cannot be null");
            Preconditions.nonNull(yTolerance, "Y Tolerance cannot be null");
            Preconditions.nonNull(zTolerance, "Z Tolerance cannot be null");

            Preconditions.ensure(minWindSpeed >= 0, "Minimum Wind Speed must be greater than or equal to 0");
            Preconditions.ensure(maxWindSpeed >= 0, "Maximum Wind Speed must be greater than or equal to 0");
            Preconditions.ensure(xTolerance >= 0, "X Tolerance must be greater than or equal to 0");
            Preconditions.ensure(yTolerance >= 0, "Y Tolerance must be greater than or equal to 0");
            Preconditions.ensure(zTolerance >= 0, "Z Tolerance must be greater than or equal to 0");

            Preconditions.ensure(minWindSpeed <= maxWindSpeed, "Minimum Wind Speed must be less than or equal to Maximum Wind Speed");

            this.minWindSpeed = minWindSpeed;
            this.maxWindSpeed = maxWindSpeed;
            this.xTolerance = xTolerance;
            this.yTolerance = yTolerance;
            this.zTolerance = zTolerance;

        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    protected WindTolerance() {
        minWindSpeed = null;
        maxWindSpeed = null;
        xTolerance = null;
        yTolerance = null;
        zTolerance = null;
    }

    public Integer getMinWindSpeed() {
        return minWindSpeed;
    }

    public Integer getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public Integer getXTolerance() {
        return xTolerance;
    }

    public Integer getYTolerance() {
        return yTolerance;
    }

    public Integer getZTolerance() {
        return zTolerance;
    }

    @Override
    public String toString() {
        return String.format("Min Wind Speed: %.2f m/s | Max Wind Speed: %.2f m/s | X Tolerance: %.2f m | Y Tolerance: %.2f m | Z Tolerance: %.2f m", minWindSpeed, maxWindSpeed, xTolerance, yTolerance, zTolerance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WindTolerance wind)) return false;
        return Objects.equals(minWindSpeed, wind.minWindSpeed) &&
                Objects.equals(maxWindSpeed, wind.maxWindSpeed) &&
                Objects.equals(xTolerance, wind.xTolerance) &&
                Objects.equals(yTolerance, wind.yTolerance) &&
                Objects.equals(zTolerance, wind.zTolerance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minWindSpeed, maxWindSpeed, xTolerance, yTolerance, zTolerance);
    }

}
