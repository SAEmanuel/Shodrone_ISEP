package authz;

import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

/**
 * A simple wrapper class for displaying user information in UI components or lists.
 * Encapsulates a {@link UserDTO} and provides a custom {@code toString()} method
 * to represent the user primarily by their ID (typically an email).
 *
 * Useful for scenarios where a user must be selected or identified in visual elements,
 * such as dropdowns or lists, while preserving access to the full {@code UserDTO}.
 */
public class UserDisplayDTO {

    /** The underlying Data Transfer Object containing user details. */
    private final UserDTO dto;

    /**
     * Constructs a UserDisplayDTO by wrapping the given {@link UserDTO}.
     *
     * @param dto The UserDTO to wrap. Must not be null.
     */
    public UserDisplayDTO(UserDTO dto) {
        this.dto = dto;
    }

    /**
     * Returns the wrapped {@link UserDTO} object.
     *
     * @return The user DTO.
     */
    public UserDTO getDto() {
        return dto;
    }

    /**
     * Returns a string representation of the user.
     * This defaults to displaying the user's identifier.
     *
     * @return String containing the user's ID (e.g., email).
     */
    @Override
    public String toString() {
        return String.format("%s", dto.getId());
    }
}
