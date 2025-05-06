package persistence.interfaces;

import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import java.util.List;

public interface AuthenticationRepository {

    boolean doLogin(String email, String password);

    void doLogout();

    boolean isLoggedIn();

    List<UserRoleDTO> getUserRoles();

    UserSession getCurrentUserSession();

    boolean addUserRole(String roleId, String description);

    boolean addUserWithRole(String name, String email, String password, String roleId);

    List<UserDTO> getAllUsers();
}
