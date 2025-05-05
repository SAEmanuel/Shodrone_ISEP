package domain.history;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class HistoryLogger<T extends IdentifiableEntity<ID>, ID extends Serializable> {

    private static final String BASE_DIR = "logs/history/";

    public void logChange(T oldEntity, T newEntity, String author) throws IOException {
        if (!Objects.equals(oldEntity.identity(), newEntity.identity())) {
            throw new IllegalArgumentException("Cannot compare entities with different identities.");
        }

        String diff = EntityDiffer.generateDiff(oldEntity, newEntity);
        if (diff.isBlank()) return;

        String fileName = BASE_DIR + newEntity.getClass().getSimpleName() + "_" + newEntity.identity() + ".log";
        Files.createDirectories(Paths.get(BASE_DIR));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("---- Modification on " + LocalDateTime.now() + " by " + author + " ----");
            writer.newLine();
            writer.write(diff);
            writer.newLine();
            writer.write("--------------------------------------------------------------");
            writer.newLine();
        }
    }
}
