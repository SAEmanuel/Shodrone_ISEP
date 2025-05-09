package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import utils.AuthUtils;

import java.time.LocalDateTime;

@Embeddable
public class DroneRemovalLog implements ValueObject {
    final static int MIN_REASON_LENGTH = 5;
    final static int MAX_REASON_LENGTH = 100;


    final String reason;
    final LocalDateTime removalDate;
    final String removedBy;

    public DroneRemovalLog(String reason) {

        try {
            Preconditions.nonEmpty(reason, "Reason should neither be null nor empty");

            Preconditions.ensure(reason.length() >= MIN_REASON_LENGTH, "Reason must have at least " + MIN_REASON_LENGTH + " characters");
            Preconditions.ensure(reason.length() <= MAX_REASON_LENGTH, "Reason must have at most " + MAX_REASON_LENGTH + " characters");

            this.reason = reason;
            this.removalDate = LocalDateTime.now();
            this.removedBy = AuthUtils.getCurrentUserEmail();

        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    protected DroneRemovalLog() {
        reason = null;
        removalDate = null;
        removedBy = null;
    }

    public String reason() {
        return this.reason;
    }

    public LocalDateTime date() {
        return this.removalDate;
    }

    public String removedBy() {
        return this.removedBy;
    }

    @Override
    public String toString() {
        return String.format("""
        📋 Drone Removal Log:
        • Reason     : %s
        • Date       : %s
        • Removed By : %s
        """, reason, removalDate, removedBy);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DroneRemovalLog)) return false;
        DroneRemovalLog that = (DroneRemovalLog) o;
        return reason.equals(that.reason) && removalDate.equals(that.removalDate) && removedBy.equals(that.removedBy);
    }

    @Override
    public int hashCode() {
        int result = reason.hashCode();
        result = 31 * result + removalDate.hashCode();
        result = 31 * result + removedBy.hashCode();
        return result;
    }

}
