package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.ANSI_BLUE;
import static more.ColorfulOutput.ANSI_BRIGHT_BLACK;
import static more.ColorfulOutput.ANSI_BRIGHT_CYAN;
import static more.ColorfulOutput.ANSI_BRIGHT_WHITE;
import static more.ColorfulOutput.ANSI_RESET;

public class StartupMessageServer {

    private static final String COMPANY_NAME = "Shodrone";
    private static final String APP_NAME = "Shodrone Server App";
    private static final String VERSION = "1.0.0";
    private static final int BORDER_WIDTH = 70;

    /**
     * Displays a stylized startup banner for the server application.
     */
    public static void displayStartupMessage(int port) {
        String border = "═".repeat(BORDER_WIDTH);
        String separator = "-".repeat(BORDER_WIDTH);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);

        System.out.println("\n\n\n");
        System.out.println(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText(COMPANY_NAME + " - Powering Drone Shows Worldwide", BORDER_WIDTH) + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println("Application        : " + ANSI_BLUE + APP_NAME + ANSI_RESET);
        System.out.println("Version            : " + ANSI_BLUE + VERSION + ANSI_RESET);
        System.out.println("Listening Port     : " + ANSI_BRIGHT_CYAN + port + ANSI_RESET);
        System.out.println("Session started at : " + ANSI_BLUE + formattedDate + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + separator + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE + centerText("Server is up and ready to receive client connections.", BORDER_WIDTH) + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK + border + ANSI_RESET);
    }

    /**
     * Centers text within a given width using spaces.
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
