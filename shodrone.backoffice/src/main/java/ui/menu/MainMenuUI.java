package ui.menu;

import ui.authz.AuthenticationUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.*;

/**
 * Main menu user interface for the application.
 * <p>
 * This class represents the primary menu that users interact with when they first launch the application.
 * It provides options for users to either log in, sign up, or view the development team details.
 * </p>
 */
public class MainMenuUI implements Runnable {

    /**
     * Constructs a new instance of the MainMenuUI.
     */
    public MainMenuUI() {
    }

    /**
     * Displays the main menu and handles user interaction.
     * <p>
     * The menu provides the following options:
     * <ul>
     *     <li>Login - Allows users to log into the application</li>
     *     <li>Sign-Up - Allows new users to sign up</li>
     *     <li>Development Team - Displays information about the development team</li>
     * </ul>
     * The menu loop continues until the user selects the exit option (-1).
     * </p>
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Login", new AuthenticationUI()));
        options.add(new MenuItem("Sign-Up", new SignUpUI()));
        options.add(new MenuItem("Development Team", new DevTeamUI()));

        int option;
        do {
            System.out.println("\n\n╔════════════════════════════════════════╗");
            option = Utils.showAndSelectIndex(options, "║" + ANSI_BRIGHT_WHITE + "               MAIN MENU               " + ANSI_RESET + " ║");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}