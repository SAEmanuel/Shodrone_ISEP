package domain.valueObjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Lob
    private byte[] videoContent;

    protected Video() {}

    public Video(byte[] videoContent) {
        if (videoContent == null || videoContent.length == 0) {
            throw new IllegalArgumentException("Video content cannot be null or empty");
        }
        this.videoContent = videoContent;
    }

    public byte[] getContent() { return videoContent; }

    @Override
    public String toString() {
        return "Video{" +
                "size=" + (videoContent != null ? videoContent.length : 0) + " bytes" +
                '}';
    }
}
