package domain.valueObjects;

public enum ShowProposalStatus {

    /**
     * Represents the status of a Show Request.
     */
    CREATED, STAND_BY, COLLABORATOR_APPROVED, CUSTOMER_APPROVED, REJECTED, PASSED_SIMULATION, FAILED_SIMULATION;

    /**
     * Returns the ShowRequestStatus corresponding to the given string.
     * Case-insensitive. Returns null if no match is found.
     *
     * @param status the string representation of the status
     * @return the corresponding ShowRequestStatus or null
     */
    public static ShowProposalStatus fromString(String status) {
        if (status == null) return null;
        try {
            return ShowProposalStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Returns a human-readable version of the status.
     *
     * @return formatted string
     */
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}



