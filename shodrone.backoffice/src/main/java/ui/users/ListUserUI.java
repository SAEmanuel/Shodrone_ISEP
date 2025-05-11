package ui.users;

import controller.ListUserController;

import java.util.List;

public class ListUserUI implements Runnable {

    private final ListUserController ctrl = new ListUserController();

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
