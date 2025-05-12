package ui.authz;

import controller.authz.AuthenticationController;
import ui.menu.MenuItem;
import ui.users.*;
import utils.*;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static more.ColorfulOutput.*;

/**
 * Handles the authentication user interface (UI) logic.
 * Displays login prompts, role selection, and redirects the authenticated user
 * to the appropriate UI based on their assigned role.
 */
public class AuthenticationUI implements Runnable {

    /** Controller responsible for authentication logic. */
    private final AuthenticationController ctrl;

    /**
     * Constructs the Authentication UI and initializes its controller.
     */
    public AuthenticationUI() {
        ctrl = new AuthenticationController();
    }

    /**
     * Executes the authentication workflow:
     * login → role selection → role-specific UI → logout.
     */
    @Override
    public void run() {
        boolean success = doLogin();

        if (success) {
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ((roles == null) || (roles.isEmpty())) {
                System.out.println("No role assigned to user.");
            } else {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role)) {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI, role);
                } else {
                    System.out.println("No role selected.");
                }
            }
        }
        this.logout();
    }

    /**
     * Builds and returns a list of available role-based menu items,
     * mapping roles to their corresponding UI components.
     *
     * @return A list of {@link MenuItem} for each supported role.
     */
    private List<MenuItem> getMenuItemForRoles() {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_ADMIN, new AdminUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CRM_COLLABORATOR, new CRMCollaboratorUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CRM_MANAGER, new CRMManagerUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_SHOW_DESIGNER, new ShowDesignerUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, new RepresentativeUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_DRONE_TECH, new DroneTechUI()));
        // TODO: Add more roles and corresponding UI as needed
        return rolesUI;
    }

    /**
     * Prompts the user for login credentials and attempts to authenticate.
     *
     * @return True if login succeeds, false otherwise.
     */
    private boolean doLogin() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE + "                 LOGIN UI               " + ANSI_RESET);

        int maxAttempts = 3;
        boolean success = false;

        do {
            maxAttempts--;
            System.out.println("Enter:");
            String id = Utils.readLineFromConsole("    •UserId/Email");
            String pwd = Utils.readLineFromConsole("    •Password");

            try {
                success = ctrl.doLogin(id, pwd);
                if (!success) {
                    System.out.println(ANSI_LIGHT_RED + "Invalid UserId and/or Password - Attempt(s) left [" + maxAttempts + "]" + ANSI_RESET + "\n");
                }
            } catch (IllegalStateException ex) {
                System.out.println(ANSI_LIGHT_RED + ex.getMessage() + ANSI_RESET + "\n");
                return false;
            } catch (Exception ex) {
                System.out.println(ANSI_LIGHT_RED + "Login failed: " + ex.getMessage() + ANSI_RESET + "\n");
                return false;
            }

        } while (!success && maxAttempts > 0);

        return success;
    }

    /**
     * Logs out the currently authenticated user.
     */
    private void logout() {
        ctrl.doLogout();
    }

    /**
     * Redirects the user to the UI associated with their selected role.
     *
     * @param rolesUI List of menu items mapping roles to UI screens.
     * @param role    The selected user role.
     */
    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role) {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found) {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription().toUpperCase());
            if (found) {
                item.run();
            }
        }
        if (!found) {
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
        }
    }

    /**
     * Prompts the user to select a role when multiple roles are assigned.
     * Returns the role directly if only one exists.
     *
     * @param roles List of roles assigned to the user.
     * @return The selected {@link UserRoleDTO}, or null if none selected.
     */
    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }
}
