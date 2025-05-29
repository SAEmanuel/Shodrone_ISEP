package ui;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.*;


/**
 * User Interface for displaying the startup message for the customer application.
 * <p>
 * This class is responsible for displaying a formatted startup message to the user,
 * which includes application information and the session start date.
 * </p>
 */
public class StartupMessageCustomerAppUI {

    private static final String COMPANY_NAME = "Shodrone";
    private static final String APP_NAME = "Shodrone Customer App";
    private static final String VERSION = "0.1.0";
    private static final int BORDER_WIDTH = 66;

    /**
     * Displays the startup message including company information, application version, and session start date.
     */
    public static void display() {
        String border = "═".repeat(BORDER_WIDTH);
        String separator = "-".repeat(BORDER_WIDTH);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        System.out.println(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText(COMPANY_NAME + " - Innovating Drone Shows", BORDER_WIDTH) + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println("Application        : " + ANSI_BLUE + APP_NAME + ANSI_RESET);
        System.out.println("Version            : " + ANSI_BRIGHT_CYAN + VERSION + ANSI_RESET);
        System.out.println("Session started on : " + ANSI_BLUE + formattedDate + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText("Welcome to the Customer App. Enjoy your drone show experience!", BORDER_WIDTH) + ANSI_RESET);
        System.out.print(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
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
