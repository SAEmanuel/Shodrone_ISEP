package domain.valueObjects;

public enum DroneStatus {
    ACTIVE,
    INACTIVE,
    MAINTENANCE,
    RESERVED,
    IN_USE,
    LOST;

    public static DroneStatus fromString(String status) {
        if (status == null) return null;
        try {
            return DroneStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
