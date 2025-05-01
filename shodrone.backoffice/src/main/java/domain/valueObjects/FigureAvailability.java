package domain.valueObjects;

public enum FigureAvailability {
    PUBLIC,
    EXCLUSIVE;

    /**
     * Returns the FigureAvailability corresponding to the given string.
     * Case-insensitive. Returns null if no match is found.
     *
     * @param status the string representation of the status
     * @return the corresponding FigureAvailability or null
     */
    public static FigureAvailability fromString(String status) {
        if (status == null) return null;
        try {
            return FigureAvailability.valueOf(status.trim().toUpperCase());
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
