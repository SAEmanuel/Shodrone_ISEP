package authz;

import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

public class UserDisplayDTO {
    private final UserDTO dto;

    public UserDisplayDTO(UserDTO dto) {
        this.dto = dto;
    }

    public UserDTO getDto() {
        return dto;
    }

    @Override
    public String toString() {
        return String.format("%s", dto.getId());
    }
}
