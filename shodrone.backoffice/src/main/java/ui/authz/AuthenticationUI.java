package ui.authz;

import ui.*;
import controller.AuthenticationController;
import ui.menu.MenuItem;
import utils.*;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static more.ColorfulOutput.*;



public class AuthenticationUI implements Runnable {
    private final AuthenticationController ctrl;

    public AuthenticationUI() {
        ctrl = new AuthenticationController();
    }

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

    private List<MenuItem> getMenuItemForRoles() {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_ADMIN, new AdminUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CRM_COLLABORATOR, new CRMCollaboratorUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CRM_MANAGER, new CRMManagerUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_SHOW_DESIGNER, new ShowDesignerUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, new RepresentativeUI()));

        //TODO: Complete with other user roles and related RoleUI
        return rolesUI;
    }

    private boolean doLogin() {
        System.out.println("\n\n══════════════════════════════════════════");
        System.out.println(ANSI_BRIGHT_WHITE+"                 LOGIN UI               "+ANSI_RESET);


        int maxAttempts = 3;
        boolean success = false;
        do {
            maxAttempts--;
            System.out.println("Enter:");
            String id = Utils.readLineFromConsole("    •UserId/Email");
            String pwd = Utils.readLineFromConsole("    •Password");

            success = ctrl.doLogin(id, pwd);
            if (!success) {
                System.out.println(ANSI_LIGHT_RED+"Invalid UserId and/or Password - Attempt(s) left [" + maxAttempts + "]" + ANSI_RESET+"\n");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    private void logout() {
        ctrl.doLogout();
    }

    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role) {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found) {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found) {
                item.run();
            }
        }
        if (!found) {
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
        }
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }
}