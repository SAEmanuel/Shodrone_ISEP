package domain.valueObjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

@Embeddable
@Getter
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String videoPath;

    @Transient
    private static final String BASE_PATH = "C:/videos/";

    protected Video() {}

    public Video(String originalFileName, byte[] videoContent) {
        try {
            File dir = new File(BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path targetPath = Path.of(BASE_PATH + System.currentTimeMillis() + "_" + originalFileName);
            Files.write(targetPath, videoContent);
            this.videoPath = targetPath.toAbsolutePath().toString();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar vídeo no disco", e);
        }
    }

    @Override
    public String toString() {
        return videoPath;
    }
}
