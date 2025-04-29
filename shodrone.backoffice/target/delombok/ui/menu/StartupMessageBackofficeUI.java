package ui.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.*;

public class StartupMessageBackofficeUI {

    private static final String COMPANY_NAME = "Shodrone";
    private static final String APP_NAME = "Shodrone Back-Office";
    private static final String VERSION = "0.1.0"; // Baseado no pom.xml
    private static final int BORDER_WIDTH = 66;

    public static void display() {
        String border = "═".repeat(BORDER_WIDTH);
        String separator = "-".repeat(BORDER_WIDTH);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        System.out.println(ANSI_BRIGHT_BLACK+border+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE+centerText(COMPANY_NAME + " - Innovating Drone Shows", BORDER_WIDTH)+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK+separator+ANSI_RESET);
        System.out.println("Application:         " +ANSI_BLUE+ APP_NAME + ANSI_RESET);
        System.out.println("Version:             " +ANSI_BRIGHT_CYAN+ VERSION+ANSI_RESET);
        System.out.println("Session started on:  " +ANSI_BLUE+ formattedDate+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK+separator+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_WHITE+centerText("Welcome to " + APP_NAME + ". Ready to manage your drone shows.", BORDER_WIDTH)+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_BLACK+border+ANSI_RESET);
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        if (padding <= 0) {
            return text;
        }
        return " ".repeat(padding) + text;
    }

}
