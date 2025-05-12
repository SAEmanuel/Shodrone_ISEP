package ui.users;

import ui.menu.ManageUserUI;
import ui.menu.MenuItem;
import ui.menu.SignUpUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

/**
 * UI class for administrators.
 * Presents a menu with administrative options such as managing users,
 * registering new users, and listing existing users.
 */
public class AdminUI implements Runnable {

    /**
     * Default constructor.
     */
    public AdminUI() {
    }

    /**
     * Runs the admin menu loop, allowing selection and execution of admin tasks.
     */
    @Override
    public void run() {
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
}
