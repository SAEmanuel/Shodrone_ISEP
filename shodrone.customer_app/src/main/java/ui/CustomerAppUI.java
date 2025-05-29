package ui;

import network.AuthenticationController;
import utils.Utils;

import static more.ColorfulOutput.*;

public class CustomerAppUI {

    private final AuthenticationController ctrl;

    public CustomerAppUI(AuthenticationController ctrl) {
        this.ctrl = ctrl;
    }

    public void run() {
        System.out.println(ANSI_BRIGHT_WHITE + "\nWelcome to the Customer App!\n" + ANSI_RESET);

        if (doLogin()) {
            System.out.println(ANSI_BRIGHT_GREEN + "Login successful! Welcome!" + ANSI_RESET);
            new RepresentativeUI().run();
        } else {
            System.out.println(ANSI_LIGHT_RED + "Login failed. Exiting application." + ANSI_RESET);
        }
    }

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
}
