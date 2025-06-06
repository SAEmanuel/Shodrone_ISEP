package network;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple DTO to send a response message from the server.
 * Used for success or failure notifications.
 */
@Setter
@Getter
public class ResponseDTO {
    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(String message) {
        this.message = message;
    }

}
