package history;

import persistence.IdentifiableEntity;
import utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Utility class for logging changes and creation events for entities that implement the IdentifiableEntity interface.
 * The log entries are stored in a file for each entity, and include details such as the timestamp, author,
 * entity ID, and the changes made or the initial state.
 *
 * @param <T> The type of entity being logged. This must extend IdentifiableEntity and be Serializable.
 * @param <ID> The type of the entity's identifier, which must be Serializable.
 */
public class HistoryLogger<T extends IdentifiableEntity<ID>, ID extends Serializable> {

    private static final String BASE_DIR = "logs/ObjectsHistory/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs changes between two versions of the same entity.
     * It compares the two entities and generates a diff of the changes. If changes are detected, the log entry is created
     * and written to the corresponding log file.
     *
     * @param oldEntity The original version of the entity.
     * @param newEntity The updated version of the entity.
     * @param author The author who made the changes.
     * @throws IOException If an I/O error occurs while writing the log entry.
     * @throws IllegalArgumentException If the entities have different identities.
     */
    public void logChange(T oldEntity, T newEntity, String author) throws IOException {
        if (!Objects.equals(oldEntity.identity(), newEntity.identity())) {
            throw new IllegalArgumentException("Cannot compare entities with different identities.");
        }

        // Generate a diff of the changes between the two entities
        String diff = EntityDiffer.generateDiff(oldEntity, newEntity);
        if (diff.isBlank()) return; // If no changes, do not log anything

        // Prepare the log entry with all the details
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(Utils.centeredTitle("ENTITY MODIFIED").concat("\n"));
        logEntry.append(Utils.bottomBoxLine()).append("\n");
        logEntry.append("Timestamp : ").append(FORMATTER.format(LocalDateTime.now())).append("\n");
        logEntry.append("Author    : ").append(author).append("\n");
        logEntry.append("Entity ID : ").append(newEntity.identity()).append("\n");
        logEntry.append("Changes   :\n").append(diff).append("\n");
        logEntry.append("═════════════════════════════════════════════\n");

        // Write the log entry to the log file
        writeLog(newEntity, logEntry.toString());
    }

    /**
     * Logs the creation of a new entity.
     * The log entry contains information about the entity's initial state when it was created.
     *
     * @param newEntity The newly created entity.
     * @param author The author who created the entity.
     * @throws IOException If an I/O error occurs while writing the log entry.
     */
    public void logCreation(T newEntity, String author) throws IOException {
        // Prepare the log entry with details about the entity's creation
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(Utils.centeredTitle("ENTITY CREATED").concat("\n"));
        logEntry.append(Utils.bottomBoxLine()).append("\n");
        logEntry.append("Timestamp : ").append(FORMATTER.format(LocalDateTime.now())).append("\n");
        logEntry.append("Author    : ").append(author).append("\n");
        logEntry.append("Entity ID : ").append(newEntity.identity()).append("\n");
        logEntry.append("Initial State:\n").append(newEntity.toString()).append("\n");
        logEntry.append("═════════════════════════════════════════════\n");

        // Write the log entry to the log file
        writeLog(newEntity, logEntry.toString());
    }

    /**
     * Internal method to write the log entry to the corresponding file.
     * The log file is named based on the entity's class name and ID.
     *
     * @param entity The entity whose change or creation is being logged.
     * @param logContent The content of the log entry.
     * @throws IOException If an I/O error occurs while writing the log content to the file.
     */
    private void writeLog(T entity, String logContent) throws IOException {
        // Determine the file path based on the entity's class and identity
        String fileName = BASE_DIR + entity.getClass().getSimpleName() + "_" + entity.identity() + ".log";
        Files.createDirectories(Paths.get(BASE_DIR)); // Ensure that the directory exists

        // Write the log content to the log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(logContent);
            writer.newLine();
        }
    }
}
