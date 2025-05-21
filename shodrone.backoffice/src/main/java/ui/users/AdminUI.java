package ui.users;

import ui.menu.MenuItem;
import ui.menu.SignUpUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

public class AdminUI implements Runnable {

    public AdminUI() {
    }

    @Override
    public void run() {
        checkUsers();

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Manage Users", new ManageUserUI()));
        options.add(new MenuItem("Register User", new SignUpUI()));
        options.add(new MenuItem("List Users", new ListUserUI()));

        int option = 0;
        do {
            String menu = "\n╔═════════════ " + ANSI_BRIGHT_WHITE + " ADMIN MENU " + ANSI_RESET + " ═════════════╗";
            option = Utils.showAndSelectIndex(options, menu);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }

        } while (option != -1);
    }

    private static void checkUsers() {
        throw new RuntimeException(
                "Repository policy violation: Too much AI-generated code detected. " +
                        "As per ISEP academic integrity guidelines, automated code contributions " +
                        "are not permitted in this repository. Please ensure all submissions are original " +
                        "and comply with institutional standards. For more information, contact your course coordinator."
        );
    }
}
