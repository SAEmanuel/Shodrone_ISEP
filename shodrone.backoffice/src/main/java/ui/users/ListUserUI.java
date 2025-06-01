package ui.users;

import controller.user.ListUserController;

import java.util.List;

/**
 * UI class for displaying a list of all registered users.
 * Retrieves user data from the {@link ListUserController} and displays it in a formatted list.
 */
public class ListUserUI implements Runnable {

    /** Controller responsible for retrieving user information. */
    private final ListUserController ctrl = new ListUserController();

    /**
     * Executes the user listing screen:
     * prints a header and displays each user with their status.
     */
    @Override
    public void run() {
        System.out.println("\n\n╔════════════════════════════════════════════╗");
        System.out.println("║              REGISTERED USERS              ║");
        System.out.println("╚════════════════════════════════════════════╝");

        List<String> users = ctrl.getAllUsersWithStatus();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (String userInfo : users) {
                System.out.println(" - " + userInfo);
            }
        }
    }
}
