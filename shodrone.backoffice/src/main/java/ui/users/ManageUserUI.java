package ui.users;

import authz.UserDisplayDTO;
import controller.user.ManageUserController;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;
import utils.Utils;

import java.util.List;

public class ManageUserUI implements Runnable {

    private final ManageUserController ctrl = new ManageUserController();

    @Override
    public void run() {
        System.out.println("\n╔════════════════════════════════════════╗");
        List<String> filterOptions = List.of("Active users", "Inactive users", "All users");
        System.out.print("║               USER FILTER              ║");
        int filterIndex = Utils.showAndSelectIndex(filterOptions, "");

        if (filterIndex == -1) return;

        List<UserDTO> allUsers = ctrl.getAllUsers();

        List<UserDTO> filteredUsers = allUsers.stream()
                .filter(user -> {
                    boolean active = ctrl.isUserActive(user);
                    return switch (filterIndex) {
                        case 0 -> active;
                        case 1 -> !active;
                        default -> true;
                    };
                })
                .toList();

        if (filteredUsers.isEmpty()) {
            System.out.println("No users match the selected filter.");
            return;
        }

        List<UserDisplayDTO> displayList = filteredUsers.stream()
                .map(UserDisplayDTO::new)
                .toList();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.print("║               SELECT USER              ║");
        UserDisplayDTO selectedDisplay = (UserDisplayDTO) Utils.showAndSelectOne(displayList, "");
        if (selectedDisplay == null) return;

        UserDTO selected = selectedDisplay.getDto();
        if (selected == null) return;

        boolean isActive = ctrl.isUserActive(selected);
        System.out.println("\nUser: " + selected.getId());
        System.out.println("Current state: " + (isActive ? "ACTIVE" : "INACTIVE"));

        List<String> actions = List.of("Activate", "Deactivate");
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.print("║              SELECT OPTION             ║");
        int actionIndex = Utils.showAndSelectIndex(actions, "");
        if (actionIndex == -1) return;

        if (actionIndex == 0) {
            if (isActive) {
                System.out.println("ℹ️  User is already active.");
            } else {
                ctrl.activateUser(selected);
                System.out.println("✅ User activated.");
            }
        } else if (actionIndex == 1) {
            if (!isActive) {
                System.out.println("ℹ️  User is already inactive.");
            } else {
                ctrl.deactivateUser(selected);
                System.out.println("✅ User deactivated.");
            }
        } else {
            System.out.println("❌ Invalid option.");
        }
    }
}
