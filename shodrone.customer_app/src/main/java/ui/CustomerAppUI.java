package ui;

import controller.network.AuthenticationController;
import utils.Utils;

import static more.ColorfulOutput.*;

/**
 * UI class responsible for initializing the Customer Representative application.
 * <p>
 * Handles the login process and launches the appropriate interface (e.g., {@link ui.RepresentativeUI})
 * upon successful authentication.
 *
 * <p>Allows up to three login attempts before terminating the application.</p>
 *
 * @author
 */
public class CustomerAppUI {

    /**
     * Controller used for handling authentication with the server.
     */
    private final AuthenticationController ctrl = new AuthenticationController();

    /**
     * Default constructor.
     */
    public CustomerAppUI() {
    }

    /**
     * Main entry point for running the customer application UI.
     * <p>
     * If authentication is successful, the user is welcomed and redirected
     * to the {@link RepresentativeUI}. Otherwise, the application exits.
     */
    public void run() {
        if (doLogin()) {
            System.out.println(ANSI_BRIGHT_GREEN + "\nLogin successful! Welcome!" + ANSI_RESET);
            new RepresentativeUI(ctrl).run();
        } else {
            System.out.println(ANSI_LIGHT_RED + "\nFATAL: Login failed. Exiting application." + ANSI_RESET);
        }
    }

    /**
     * Displays the login UI and handles the authentication process.
     * <p>Allows up to 3 attempts for login. Credentials are read from console input.
     * Uses the {@link AuthenticationController#doLogin(String, String)} method to authenticate.</p>
     *
     * @return {@code true} if login is successful, {@code false} otherwise.
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
                    System.out.println(ANSI_LIGHT_RED + "\nInvalid UserId and/or Password - Attempt(s) left [" + maxAttempts + "]" + ANSI_RESET + "\n");
                }
            } catch (IllegalStateException ex) {
                System.out.println(ANSI_LIGHT_RED + ex.getMessage() + ANSI_RESET + "\n");
                return false;
            } catch (Exception ex) {
                System.out.println(ANSI_LIGHT_RED + "\nLogin failed: " + ex.getMessage() + ANSI_RESET + "\n");
                return false;
            }

        } while (!success && maxAttempts > 0);

        return success;
    }
}
