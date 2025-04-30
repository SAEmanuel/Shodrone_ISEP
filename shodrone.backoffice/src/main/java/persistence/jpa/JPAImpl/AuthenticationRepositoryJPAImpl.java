package persistence.jpa.JPAImpl;

import authz.*;
import persistence.jpa.AuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationRepositoryJPAImpl extends JpaBaseRepository<User, Email> implements AuthenticationRepository {

    private User loggedUser;
    private final UserRoleRepository roleRepository;

    public AuthenticationRepositoryJPAImpl() {
        super();
        this.roleRepository = new UserRoleRepository();
    }

    @Override
    public boolean doLogin(String emailString, String passwordString) {
        Email email = new Email(emailString);
        User user = findById(email);
        if (user != null && user.hasPassword(passwordString)) {
            this.loggedUser = user;
            return true;
        }
        return false;
    }

    @Override
    public void doLogout() {
        this.loggedUser = null;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedUser != null;
    }

    @Override
    public List<UserRoleDTO> getUserRoles() {
        if (!isLoggedIn()) {
            return null;
        }
        return loggedUser.getRoles().stream()
                .map(role -> new UserRoleDTO(role.getId(), role.getDescription()))
                .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        return loggedUser;
    }

    public void saveUser(User user) {
        add(user);
    }

    public void saveRole(UserRole role) {
        roleRepository.save(role);
    }

    public List<UserRole> getAllRoles() {
        return roleRepository.findAll();
    }

    public UserRoleRepository getRoleRepository() {
        return this.roleRepository;
    }

}
