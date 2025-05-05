package domain.history;

import utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import static more.ColorfulOutput.*;

public class HistoryLogger<T extends IdentifiableEntity<ID>, ID extends Serializable> {

    private static final String BASE_DIR = "logs/ObjectsHistory/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs changes between two versions of the same entity.
     */
    public void logChange(T oldEntity, T newEntity, String author) throws IOException {
        if (!Objects.equals(oldEntity.identity(), newEntity.identity())) {
            throw new IllegalArgumentException("Cannot compare entities with different identities.");
        }

        String diff = EntityDiffer.generateDiff(oldEntity, newEntity);
        if (diff.isBlank()) return;

        StringBuilder logEntry = new StringBuilder();
        logEntry.append(Utils.centeredTitle("ENTITY MODIFIED").concat("\n"));
        logEntry.append(Utils.bottomBoxLine()).append("\n");
        logEntry.append("Timestamp : ").append(FORMATTER.format(LocalDateTime.now())).append("\n");
        logEntry.append("Author    : ").append(author).append("\n");
        logEntry.append("Entity ID : ").append(newEntity.identity()).append("\n");
        logEntry.append("Changes   :\n").append(diff).append("\n");
        logEntry.append("═════════════════════════════════════════════\n");

        writeLog(newEntity, logEntry.toString());
    }

    /**
     * Logs the creation of a new entity.
     */
    public void logCreation(T newEntity, String author) throws IOException {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(Utils.centeredTitle("ENTITY CREATED").concat("\n"));
        logEntry.append(Utils.bottomBoxLine()).append("\n");
        logEntry.append("Timestamp : ").append(FORMATTER.format(LocalDateTime.now())).append("\n");
        logEntry.append("Author    : ").append(author).append("\n");
        logEntry.append("Entity ID : ").append(newEntity.identity()).append("\n");
        logEntry.append("Initial State:\n").append(newEntity.toString()).append("\n");
        logEntry.append("═════════════════════════════════════════════\n");

        writeLog(newEntity, logEntry.toString());
    }

    /**
     * Internal method to write the log entry to the corresponding file.
     */
    private void writeLog(T entity, String logContent) throws IOException {
        String fileName = BASE_DIR + entity.getClass().getSimpleName() + "_" + entity.identity() + ".log";
        Files.createDirectories(Paths.get(BASE_DIR));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(logContent);
            writer.newLine();
        }
    }
}
