package ui.menu;

import controller.StartupMessageBackofficeController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

/**
 * User Interface for displaying the startup message for the backoffice application.
 * <p>
 * This class is responsible for displaying a formatted startup message to the user,
 * which includes application information, the session start date, and the persistence type (file or database).
 * </p>
 */
public class StartupMessageBackofficeUI {

    private static StartupMessageBackofficeController controller;

    /**
     * Private constructor to initialize the controller.
     */
    private StartupMessageBackofficeUI() {
        controller = getController();
    }

    /**
     * Retrieves the controller instance.
     *
     * @return the StartupMessageBackofficeController instance
     */
    private static StartupMessageBackofficeController getController() {
        if (controller == null) {
            controller = new StartupMessageBackofficeController();
        }
        return controller;
    }

    // Constant fields for company and application information
    private static final String COMPANY_NAME = "Shodrone";
    private static final String APP_NAME = "Shodrone Back-Office";
    private static final String VERSION = "0.1.0"; // Based on pom.xml
    private static final int BORDER_WIDTH = 66;
    private static final boolean TYPE_PERSISTENCE = getController().displayPersistenceOptions();

    /**
     * Displays the startup message including company information, application version, session start date,
     * and persistence type.
     *
     * @return the persistence type (true if file-based, false if H2 database)
     */
    public static boolean display() {
        String border = "═".repeat(BORDER_WIDTH);
        String separator = "-".repeat(BORDER_WIDTH);

        // Get current date and format it
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        // Print the startup message
        System.out.println(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText(COMPANY_NAME + " - Innovating Drone Shows", BORDER_WIDTH) + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println("Application        : " + ANSI_BLUE + APP_NAME + ANSI_RESET);
        System.out.println("Version            : " + ANSI_BRIGHT_CYAN + VERSION + ANSI_RESET);
        System.out.println("Session started on : " + ANSI_BLUE + formattedDate + ANSI_RESET);
        printPersistenceType();
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText("Welcome to " + APP_NAME + ". Ready to manage your drone shows.", BORDER_WIDTH) + ANSI_RESET);
        System.out.print(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
        return TYPE_PERSISTENCE;
    }

    /**
     * Prints the persistence type (either FILE or H2 database) based on the controller's configuration.
     */
    private static void printPersistenceType() {
        if (TYPE_PERSISTENCE) {
            System.out.printf("Persistence type   : %s%s%s   |   %s%s%s%n", ANSI_GREEN_YELLOW + BOLD, "✔ FILE", ANSI_RESET, ANSI_LIGHT_RED + BOLD, "✖ H2db", ANSI_RESET);
        } else {
            System.out.printf("Persistence type   : %s%s%s   |   %s%s%s%n", ANSI_LIGHT_RED + BOLD, "✖ FILE", ANSI_RESET, ANSI_GREEN_YELLOW + BOLD, "✔ H2db", ANSI_RESET);
        }
    }

    /**
     * Centers the given text within the specified width by adding padding spaces.
     *
     * @param text  the text to center
     * @param width the total width for centering
     * @return the centered text
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        if (padding <= 0) {
            return text;
        }
        return " ".repeat(padding) + text;
    }
}
