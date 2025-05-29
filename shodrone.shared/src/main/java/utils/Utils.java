package utils;


import domain.entity.*;
import domain.valueObjects.Address;
import domain.valueObjects.NIF;
import domain.valueObjects.PhoneNumber;
import eapli.framework.general.domain.model.EmailAddress;
import more.ListDisplayable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.function.Function;

import static more.ColorfulOutput.*;
import static more.TextEffects.*;


public class Utils {
    final static String COLOR_OPTIONS = ANSI_BRIGHT_BLACK;
    final static int STEP = 10;

    /**
     * Immediately exits the application with a given error message.
     *
     * @param message The error message to display before exiting.
     */
    public static void exitImmediately(String message) {
        System.out.printf("%n%s%s%s%n%n", ANSI_BRIGHT_RED, message, ANSI_RESET);
        throw new RuntimeException("Exiting application");
    }

    /**
     * Silently exits the application without any message.
     */
    public static void silentExit() {
        throw new RuntimeException();
    }

    /**
     * Prints the specified number of empty lines.
     *
     * @param nLines Number of lines to drop.
     */
    public static void dropLines(int nLines) {
        if (nLines > 0) {
            System.out.print("\n".repeat(nLines));
        }
    }

    /**
     * Prints a message with the content of an Optional if the message is non-null and not empty.
     *
     * @param message         The label/message to print.
     * @param optionalMessage The Optional containing the content to print.
     */
    public static void printOptionalValidMessage(String message, Optional<?> optionalMessage) {
        if (message != null && !message.isEmpty()) {
            System.out.printf("%s%s: %s%s%n", ANSI_GREEN, message, optionalMessage.get().toString(), ANSI_RESET);
        }
    }

    /**
     * Prints a centered title with a decorative border and colored formatting.
     * Adds a "MENU" subtitle centered below the main title.
     *
     * @param title The title text to print.
     */
    public static void printCenteredTitle(String title) {
        int lineLength = 50;  // Total line length

        dropLines(2); // Print two empty lines before starting

        // Print the "═" line in bright black
        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("═");
        }
        System.out.println(ANSI_RESET); // Reset color after the line

        // Prepare and print the centered title with color and bold
        int leftSpaces = (lineLength - title.length()) / 2;
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.println(BOLD + ANSI_MEDIUM_SPRING_GREEN + title + ANSI_RESET);

        // Prepare and print "MENU" centered below the title, also bold and same color
        String menu = "MENU";
        leftSpaces = (lineLength - menu.length()) / 2;
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.println(BOLD + ANSI_MEDIUM_SPRING_GREEN + menu + ANSI_RESET);

        System.out.println(); // Final spacing
    }

    /**
     * Prints a centered subtitle with decorative lines.
     *
     * @param subtitle The subtitle string to print.
     */
    public static void printCenteredSubtitle(String subtitle) {
        int lineLength = 30;

        // Linha superior decorativa
        System.out.print("\n" + ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET);

        // Subtítulo centralizado
        int leftSpaces = (lineLength - subtitle.length()) / 2;
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.println(ANSI_MEDIUM_SPRING_GREEN + subtitle + ANSI_RESET);

        // Linha inferior decorativa
        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET + "\n");
    }

    /**
     * Prints a drone-themed centered title with decorative lines and side markers.
     * Adds a centered "MENU" subtitle with same styling.
     *
     * @param title The title text to print.
     */
    public static void printDroneCenteredTitle(String title) {
        int lineLength = 50;

        dropLines(2);

        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET);

        // Título centralizado com marcadores * nas laterais
        int leftSpaces = (lineLength - title.length() - 2) / 2; // Ajuste para marcadores
        System.out.print(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.print(BOLD + ANSI_MEDIUM_SPRING_GREEN + title + ANSI_RESET);
        System.out.print(" ".repeat(Math.max(0, lineLength - title.length() - 2 - leftSpaces)));
        System.out.println(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);

        // "MENU" centralizado com marcadores * nas laterais
        String menu = "MENU";
        leftSpaces = (lineLength - menu.length() - 2) / 2; // Ajuste para marcadores
        System.out.print(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.print(BOLD + ANSI_MEDIUM_SPRING_GREEN + menu + ANSI_RESET);
        System.out.print(" ".repeat(Math.max(0, lineLength - menu.length() - 2 - leftSpaces)));
        System.out.println(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);


        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET);
    }

    /**
     * Prints a centered subtitle with decorative lines and side '*' markers,
     * styled with teal color and bold text.
     *
     * @param subtitle The subtitle text.
     */
    public static void printCenteredSubtitleV2(String subtitle) {
        int lineLength = 30;

        dropLines(1);

        // Linha superior decorativa com ─
        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET);

        // Subtítulo centralizado com marcadores *
        int leftSpaces = (lineLength - subtitle.length() - 2) / 2; // Ajuste para marcadores
        System.out.print(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);
        System.out.print(" ".repeat(Math.max(0, leftSpaces)));
        System.out.print(ANSI_TEAL.concat(BOLD).concat(subtitle).concat(ANSI_RESET));
        System.out.print(" ".repeat(Math.max(0, lineLength - subtitle.length() - 2 - leftSpaces)));
        System.out.println(ANSI_BRIGHT_BLACK + "*" + ANSI_RESET);

        // Linha inferior decorativa com ─
        System.out.print(ANSI_BRIGHT_BLACK);
        for (int i = 0; i < lineLength; i++) {
            System.out.print("─");
        }
        System.out.println(ANSI_RESET);
    }

    /**
     * Prints a subtitle line with a bullet point, styled italic and bright black.
     *
     * @param prompt The subtitle text.
     */
    static public void printSubTitle(String prompt) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(prompt).concat(ANSI_RESET).concat(":"));
    }

    /**
     * Reads an integer input from the console with a prompt.
     * Keeps retrying until a valid integer is entered.
     *
     * @param prompt The prompt message displayed to the user.
     * @return The integer entered by the user.
     */
    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                printAlterMessage("Invalid number. Please enter a valid integer value.");
            }
        } while (true);
    }

    /**
     * Reads a strictly positive integer (> 0) from the console with prompt.
     * Keeps retrying until the user inputs a positive integer.
     *
     * @param prompt The prompt message displayed to the user.
     * @return The positive integer entered by the user.
     */
    public static int readIntegerFromConsolePositive(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                int value = Integer.parseInt(input);

                if (value <= 0) {
                    printAlterMessage("Please enter a positive integer greater than zero.");
                    continue;
                }

                return value;
            } catch (NumberFormatException ex) {
                printAlterMessage("Invalid number. Please enter a valid integer value.");
            }
        } while (true);
    }

    /**
     * Reads a non-negative integer (≥ 0) from the console with prompt.
     * Keeps retrying if the input is invalid or negative.
     *
     * @param prompt The prompt message displayed to the user.
     * @return The non-negative integer entered.
     */
    static public double readPositiveDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                if (value < 0) {
                    printFailMessage("Invalid number. Please enter a positive value.");
                    System.out.println();
                    continue;
                }

                return value;
            } catch (NumberFormatException ex) {
                printFailMessage("Invalid input. Please enter a valid value.");
                System.out.println();
            }
        } while (true);
    }

    /**
     * Reads a double (decimal number) input from the console with prompt.
     * Retries until valid input is given.
     *
     * @param prompt The prompt message displayed to the user.
     * @return The double value entered.
     */
    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                printAlterMessage("Invalid number. Please enter a valid decimal number.");
                Utils.dropLines(1);
            }
        } while (true);
    }

    /**
     * Reads a single line of text from the console after displaying the prompt.
     *
     * @param prompt The prompt message displayed to the user.
     * @return The string entered by the user.
     */
    static public long readLongFromConsole(String prompt) {
        while (true) {
            try {
                String input = readLineFromConsole(prompt);
                return Long.parseLong(input);
            } catch (NumberFormatException ex) {
                printAlterMessage("❌ Invalid number. Please enter a valid long value.");
            }
        }
    }

    /**
     * Reads a NIF (some kind of ID) from the console after prompting the user.
     * Keeps retrying until a valid NIF is entered (validated by NIF constructor).
     *
     * @param prompt The prompt message to display to the user.
     * @return A valid NIF object created from user input.
     */
    static public NIF readNIFFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                return new NIF(input);
            } catch (IllegalArgumentException ex) {
                printAlterMessage("❌".concat(ex.getMessage()).concat("Please enter a valid NIF value."));
            }
        } while (true);
    }

    /**
     * Reads a date and time from the console in the format "yyyy-MM-dd HH:mm".
     * The date/time must be at least 72 hours ahead of the current time.
     * Keeps retrying until a valid date/time meeting this criteria is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return The LocalDateTime object parsed from user input.
     */
    static public LocalDateTime readDateFromConsole(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        do {
            try {
                String strDate = readLineFromConsole(prompt);
                LocalDateTime inputDateTime = LocalDateTime.parse(strDate, formatter);

                LocalDateTime now = LocalDateTime.now();
                LocalDateTime minAllowed = now.plusHours(72);

                if (inputDateTime.isBefore(minAllowed)) {
                    printAlterMessage("The date/time must be at least 72 hours from now (" + minAllowed.format(formatter) + ").");
                    continue;
                }

                return inputDateTime;

            } catch (DateTimeParseException ex) {
                printAlterMessage("Invalid date/time. Please use format yyyy-MM-dd HH:mm (e.g., 2025-05-01 14:30).");
            }
        } while (true);
    }

    /**
     * Displays details of a drone model if present and asks user to confirm correctness.
     *
     * @param drone Optional containing the drone model to show.
     * @return true if user confirms the model, false if empty or user declines.
     */
    static public boolean showDroneModelDetails(Optional<DroneModel> drone) {

        if (drone.isEmpty()) {
            return false;
        }

        System.out.println(drone.get());
        return confirm("Is that the correct drone model? (y/n)");
    }

    /**
     * Displays details of a drone if present and asks user to confirm correctness.
     *
     * @param drone Optional containing the drone to show.
     * @return true if user confirms the drone, false if empty or user declines.
     */
    static public boolean showDroneDetails(Optional<Drone> drone) {

        if (drone.isEmpty()) {
            return false;
        }

        System.out.println(drone.get());
        return confirm("Is that the correct drone? (y/n)");
    }

    /**
     * Shows a subtitle and lists drone models to the user, then lets them select one by index.
     * Exits silently if no selection is made.
     *
     * @param droneModels List of drone models to select from.
     * @return The index of the selected drone model.
     */
    static public int selectDroneModelIndex(List<DroneModel> droneModels) {
        Utils.dropLines(1);

        Utils.printCenteredSubtitleV2("Drone Model Selection");

        int index = Utils.showAndSelectIndexPartially(droneModels, "Select the desired drone model:");
        if (index < 0) {
            Utils.printFailMessage("No drone model selected.");
            Utils.silentExit();
        }
        return index;
    }

    /**
     * Shows a menu with custom options to the user and lets them select one.
     * Exits silently if no valid option is chosen.
     *
     * @param options List of option strings to display.
     * @return The index of the selected option.
     */
    static public int showSelectionMethodMenu(List<String> options) {

        int option = Utils.showAndSelectIndexCustomOptions(options, "What do you want to do?");
        if (option < 0) {
            Utils.silentExit();
        }

        return option;
    }


    /**
     * Asks the user for a yes/no confirmation for the given message.
     * Keeps asking until the user inputs 'y' or 'n' (case-insensitive).
     *
     * @param message The confirmation message prompt.
     * @return true if user inputs 'y', false if 'n'.
     */
    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message);
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("y");
    }

    /**
     * Displays a list of objects with a header, lets the user select one, and returns the selected object.
     *
     * @param list   The list of objects to show.
     * @param header The header message for the list.
     * @return The selected object.
     */
    static public Object showAndSelectOne(List<?> list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    /**
     * Displays a list of objects with a header, lets the user select by index.
     *
     * @param list   The list of objects to show.
     * @param header The header message.
     * @return The selected index.
     */
    static public int showAndSelectIndex(List<?> list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }


    /**
     * Similar to showAndSelectIndex but displays the list with a bigger format.
     *
     * @param list   The list to show.
     * @param header The header message.
     * @return The selected index.
     */
    static public int showAndSelectIndexBigger(List<?> list, String header) {
        showListBigger(list, header);
        return selectsIndex(list);
    }

    /**
     * Displays a larger formatted list with a decorative border.
     * Shows each object numbered and a cancel option at the end.
     *
     * @param list   The list to display.
     * @param header The header message.
     */
    private static void showListBigger(List<?> list, String header) {
        System.out.println(header);
        System.out.println("╚═════════════════════════════════════════════════╝");

        int index = 0;
        for (Object o : list) {
            index++;
            System.out.printf("║    %s(%d)%s -  %-38s║%n", COLOR_OPTIONS, index, ANSI_RESET, o.toString());
        }

        System.out.printf("║    %s(0)%s -  %-38s║%n", COLOR_OPTIONS, ANSI_RESET, "Cancel");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }

    /**
     * Shows a custom formatted list with a header and lets the user select by index.
     *
     * @param list   The list of options to show.
     * @param header The header message.
     * @return The selected index.
     */
    static public int showAndSelectIndexCustomOptions(List<?> list, String header) {
        showListCustom(list, header);
        return selectsIndex(list);
    }

    /**
     * Displays a list in a database-style formatted view with a header,
     * then prompts the user to select an item by its index.
     *
     * @param list   The list of items to display.
     * @param header The header text shown before the list.
     * @return The index of the selected item from the list.
     */
    static public int showAndSelectIndexDataBase(List<?> list, String header) {
        showListDataBase(list, header);
        return selectsIndex(list);
    }

    /**
     * Shows a database-style formatted list with a header.
     *
     * @param list   The list of entries to display.
     * @param header The header message.
     */
    static public void showListDataBase(List<?> list, String header) {
        System.out.println(header);
        System.out.println("╚═════════════════════════════════════════════════╝");

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.printf("║    %s(%-3s%s -  %-36s %-2s%n", COLOR_OPTIONS, index + ")", ANSI_RESET, o.toString(), "║");
        }
        System.out.printf("║    %s(0)%s  -  %-28s %9s%n", COLOR_OPTIONS, ANSI_RESET, "Cancel", "║");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }

    /**
     * Shows a simple formatted list with a header.
     *
     * @param list   The list to display.
     * @param header The header message.
     */
    static public void showList(List<?> list, String header) {
        System.out.println(header);
        System.out.println("╚════════════════════════════════════════╝");

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.printf("║    %s(%d)%s -  %-28s %-2s%n", COLOR_OPTIONS, index, ANSI_RESET, o.toString(), "║");
        }
        System.out.printf("║    %s(0)%s -  %-20s %9s%n", COLOR_OPTIONS, ANSI_RESET, "Cancel", "║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    /**
     * Displays a paged list of elements with a specified number of items per page.
     * After each page, asks the user if they want to continue to the next page.
     *
     * @param list                 The list of elements to display.
     * @param sizeOfObjectsPerPage Number of elements to show per page.
     * @param header               The header text to show above the list.
     */
    static public void showPagedElementList(List<?> list, int sizeOfObjectsPerPage, String header) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double) totalItems / sizeOfObjectsPerPage);

        int lineLength = 30;
        for (int page = 0; page <= totalPages; page++) {

            if (page <= totalPages) {
                Utils.dropLines(2);
                Utils.printCenteredSubtitle(header);
                System.out.printf("%s│    Page (%d)  │%s%n", COLOR_OPTIONS, (page), ANSI_RESET);
            }

            int startIndex = (page - 1) * sizeOfObjectsPerPage;
            int endIndex = Math.min(startIndex + sizeOfObjectsPerPage, totalItems);
            List<?> pageItems = list.subList(startIndex, endIndex);

            for (Object o : pageItems) {
                System.out.printf(COLOR_OPTIONS + "│" + ANSI_RESET + "%sFigure -> %-28s%s%n", ANSI_BLUE, o.toString(), ANSI_RESET);
            }

            String input;
            do {
                input = Utils.readLineFromConsole(COLOR_OPTIONS + "│    " + "(y/n)" + ANSI_RESET + " - Do you want to list one more page? ");
            } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

            //----------------------------------
            System.out.print(ANSI_BRIGHT_BLACK);
            System.out.print("╰");
            for (int i = 0; i < lineLength; i++) {
                System.out.print("─");
            }
            System.out.print("╯");
            System.out.println(ANSI_RESET);

            if (input.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    /**
     * Displays a paged list of elements starting from a specific page.
     * After each page, asks the user if they want to continue to the next page.
     *
     * @param list                 The list of elements to display.
     * @param startingPage         The page number to start displaying from (1-based).
     * @param sizeOfObjectsPerPage Number of elements to show per page.
     * @param header               The header text to show above the list.
     */
    static public void showPagedElementListByStartingPage(List<?> list, int startingPage, int sizeOfObjectsPerPage, String header) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double) totalItems / sizeOfObjectsPerPage);

        if (startingPage < 1 || startingPage > totalPages) {
            Utils.printFailMessage("No elements found starting at page " + startingPage + " with " + sizeOfObjectsPerPage + " per page -> total list size : " + list.size());
            return;
        }

        int lineLength = 30;

        for (int page = startingPage; page <= totalPages; page++) {

            if (page <= totalPages) {
                Utils.dropLines(2);
                Utils.printCenteredSubtitle(header);
                System.out.printf("%s│    Page (%d)  │%s%n", COLOR_OPTIONS, (page), ANSI_RESET);
            }

            int startIndex = (page - 1) * sizeOfObjectsPerPage;
            int endIndex = Math.min(startIndex + sizeOfObjectsPerPage, totalItems);
            List<?> pageItems = list.subList(startIndex, endIndex);

            for (Object o : pageItems) {
                System.out.printf(COLOR_OPTIONS + "│" + ANSI_RESET + "%sFigure -> %-28s%s%n", ANSI_BLUE, o.toString(), ANSI_RESET);
            }

            String input;
            if (page < totalPages) {
                do {
                    input = Utils.readLineFromConsole(COLOR_OPTIONS + "│    " + "(y/n)" + ANSI_RESET + " - Do you want to list one more page? ");
                } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

                if (input.equalsIgnoreCase("n")) {
                    break;
                }
            }
            //----------------------------------
            System.out.print(ANSI_BRIGHT_BLACK);
            System.out.print("╰");
            for (int i = 0; i < lineLength; i++) {
                System.out.print("─");
            }
            System.out.print("╯");
            System.out.println(ANSI_RESET);


        }
    }

    /**
     * Prints an alert message in bright red color.
     *
     * @param message The alert message text.
     */
    static public void printAlterMessage(String message) {
        System.out.println(ANSI_ORANGE.concat(BOLD).concat(message).concat(ANSI_RESET));
    }

    /**
     * Prints a warning message with a standard options color.
     *
     * @param message The warning message text.
     */
    static public void silentWarning(String message) {
        System.out.println(COLOR_OPTIONS + message + ANSI_RESET);
    }

    /**
     * Displays a custom formatted list with a header.
     * Each item is shown with a numbered option and an option to cancel (0).
     *
     * @param list   The list of items to display.
     * @param header The header text to show above the list.
     */
    static public void showListCustom(List<?> list, String header) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(header).concat(":") + ANSI_RESET);
        int index = 0;
        for (Object o : list) {
            index++;
            System.out.printf("    %s(%d)%2s -  %-28s%n", COLOR_OPTIONS, index, ANSI_RESET, o.toString());
        }
        System.out.printf("    %s(0)%2s -  %-20s%n", COLOR_OPTIONS, ANSI_RESET, "Cancel");
    }

    /**
     * Displays a list of elements with an optional header.
     * If elements implement ListDisplayable, uses their toListString() method for display.
     *
     * @param list   The list of elements to display.
     * @param header The header text to show above the list; if empty, no header is printed.
     */
    public static void showListElements(List<?> list, String header) {
        if (!header.isEmpty())
            System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(header).concat(":") + ANSI_RESET);

        for (Object o : list) {
            String display = (o instanceof ListDisplayable)
                    ? ((ListDisplayable) o).toListString()
                    : o.toString();

            System.out.printf("    %s  %s%-2s%s%n", "•", ANSI_BLUE, display, ANSI_RESET);
        }

        dropLines(3);
    }

    /**
     * Prompts the user to select an object from the list by entering its index.
     * Valid inputs are 0 (to cancel) or 1 to list.size().
     *
     * @param list The list of objects to choose from.
     * @return The selected object, or null if canceled or invalid input.
     */
    static public Object selectsObject(List<?> list) {
        try {
            String input;
            int value;
            int maxValue = list.size();
            do {
                input = Utils.readLineFromConsole("Type your option");
                value = Integer.valueOf(input);

                if (value < 0)
                    Utils.printFailMessage("Value lower then 0");

                if (value > maxValue)
                    Utils.printFailMessage("Value higher then " + maxValue);

            } while (value < 0 || value > list.size());

            if (value == 0) {
                return null;
            } else {
                return list.get(value - 1);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Prompts the user to select an index from the list by entering a number.
     * Accepts 0 (cancel) or any number from 1 to list.size().
     * Returns the selected index (0-based) or -1 if canceled.
     *
     * @param list The list to select from.
     * @return The zero-based selected index, or -1 if canceled.
     */
    static public int selectsIndex(List list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option");
            try {
                value = Integer.valueOf(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size());
        //dropLines(1);
        return value - 1;
    }

    /**
     * Prompts the user to select an index from the list with pagination support.
     * The valid input range is between 0 and STEP.
     * Returns the calculated global index considering the current pagination cycle.
     *
     * @param list  The list from which to select.
     * @param cycle The current page or cycle index (zero-based).
     * @return The selected index relative to the whole list.
     */
    static public int selectsIndex(List<?> list, int cycle) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option");
            try {
                value = Integer.valueOf(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > STEP);
        if (cycle > 0 && value == 0) {
            return -1;
        }
        return (value - 1) + (cycle * STEP);
    }

    /**
     * Prompts the user repeatedly until a valid input is given,
     * parsing the input string with the provided function.
     * If parsing throws an exception, prints the failure message and re-prompts.
     *
     * @param prompt        The prompt message to show.
     * @param parseFunction A function that converts String input to the desired type.
     * @param <T>           The type to parse the input into.
     * @return The successfully parsed input.
     */
    public static <T> T rePromptWhileInvalid(String prompt, Function<String, T> parseFunction) {
        T result = null;
        boolean redo;
        do {
            redo = false;
            String input = readLineFromConsole(prompt);
            try {
                result = parseFunction.apply(input);
            } catch (Exception e) {
                Utils.printFailMessage(e.getMessage());
                System.out.println();
                redo = true;
            }
        } while (redo);

        return result;
    }

    /**
     * Similar to rePromptWhileInvalid but converts input to uppercase before parsing,
     * designed specifically for enums or case-insensitive input.
     *
     * @param prompt        The prompt message to show.
     * @param parseFunction A function that converts String input to the desired type.
     * @param <T>           The type to parse the input into.
     * @return The successfully parsed input.
     */
    public static <T> T rePromptEnumWhileInvalid(String prompt, Function<String, T> parseFunction) {
        T result = null;
        boolean redo;
        do {
            redo = false;
            String input = readLineFromConsole(prompt);
            try {
                result = parseFunction.apply(input.toUpperCase());
            } catch (Exception e) {
                Utils.printFailMessage("Invalid input!");
                System.out.println();
                redo = true;
            }
        } while (redo);

        return result;
    }

    /**
     * Prints a success message in bright green color.
     *
     * @param message The success message text.
     */
    public static void printSuccessMessage(String message) {
        System.out.println(ANSI_BRIGHT_GREEN + message + ANSI_RESET);
    }

    /**
     * Prints a failure message in bright red color.
     *
     * @param message The failure message text.
     */
    public static void printFailMessage(String message) {
        System.out.println(ANSI_BRIGHT_RED + message + ANSI_RESET);
    }

    /**
     * Shows a list of objects contained inside an Optional and prompts the user to select one.
     *
     * @param optionalResult An Optional containing the list of objects.
     * @param header         The header text to display before the list.
     * @return An Optional containing the entire list if an object is selected, otherwise empty.
     */
    public static Optional<?> showAndSelectObjectFromList(Optional<List<?>> optionalResult, String header) {
        Optional<?> result = Optional.empty();
        List<?> list = optionalResult.get();

        System.out.println(ANSI_BRIGHT_BLACK.concat(BOLD).concat("• Available ".concat(header).concat(" :")).concat(ANSI_RESET));

        int index = 1;
        for (Object obj : list) {
            System.out.printf("    %s(%d)%s -  %s%n", ANSI_BRIGHT_BLACK, index++, ANSI_RESET, obj.toString());
        }
        Object obj = selectsObject(list);
        if (obj != null)
            result = Optional.of(list);

        return result;
    }

    public static Optional<?> showAndSelectObjectFromList2(Optional<List<?>> optionalResult, String header) {
        Optional<?> result = Optional.empty();
        List<?> list = optionalResult.get();

        System.out.println(ANSI_BRIGHT_BLACK.concat(BOLD).concat("• Available ".concat(header).concat(" :")).concat(ANSI_RESET));

        int index = 1;
        for (Object obj : list) {
            System.out.printf("    %s(%d)%s -  %s%n", ANSI_BRIGHT_BLACK, index++, ANSI_RESET, obj.toString());
        }
        result = Optional.ofNullable(selectsObject(list));
        return result;
    }


    /**
     * Prints a detailed summary of a ShowRequest object in a nicely formatted style.
     *
     * @param registeredShowRequest The ShowRequest object to print.
     */
    static public void printShowRequestResume(ShowRequest registeredShowRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.printf("%n%n🧾 Show Request with ID [%d] summary: %n", registeredShowRequest.identity());
        System.out.println("──────────────────────────────────────────────────");
        System.out.println("⏱️ Submitted at              : " + registeredShowRequest.getSubmissionDate().format(formatter));
        System.out.println("💬️ Status                    : " + registeredShowRequest.getStatus().toString());
        System.out.println("👤 Responsible Collaborator  : " + registeredShowRequest.getSubmissionAuthor());
        System.out.println("👥 Customer                  : " + registeredShowRequest.getCostumer().name());
        System.out.println("📝 Description               : " + registeredShowRequest.getDescription());
        System.out.println("📅 Show Date                 : " + registeredShowRequest.getShowDate().format(formatter));
        System.out.println("📍 Location                  : " + registeredShowRequest.getLocation().toString());
        System.out.println("🚁 Number of Drones          : " + registeredShowRequest.getNumberOfDrones());
        System.out.println("⏱️ Show Duration             : " + registeredShowRequest.getShowDuration().toMinutes() + " minutes");
        System.out.println("🗿 Selected Figures          :");

        int index = 1;
        for (Figure figure : registeredShowRequest.getFigures()) {
            System.out.printf("   %d. %s%n", index++, figure.toString());
        }

        System.out.println("──────────────────────────────────────────────────\n");
    }


    /**
     * Shows the list in segments (pages) of size STEP with a header, asking the user if they want to see more.
     * Continues paging until user declines or the list ends.
     * Finally prompts the user to select an index across the entire list considering the paging cycles.
     *
     * @param list   The full list to paginate and display.
     * @param header The header text to show for each segment.
     * @return The selected index relative to the full list, or -1 if none selected.
     */
    static public int showAndSelectIndexPartially(List<?> list, String header) {
        int total = list.size();
        int startIndex = 0;
        int cycle = 0;

        while (startIndex < total) {
            int endIndex = Math.min(startIndex + STEP, total);
            List<?> sublist = list.subList(startIndex, endIndex);

            showListCustom(sublist, header + " (Showing " + (startIndex + 1) + " to " + endIndex + " of " + total + ")");

            startIndex += STEP;

            if (startIndex < total) {
                boolean seeMore = Utils.confirm("Do you want to see more items? (y/n)");
                if (seeMore) {
                    cycle++;
                    continue;
                }
            }

            return selectsIndex(list, cycle);
        }

        return -1;
    }


    /**
     * Displays a list in paginated chunks of size STEP with a header.
     * After each chunk, asks the user if they want to see more items.
     * Stops displaying if user declines or all items are shown.
     *
     * @param list   The full list to display.
     * @param header The header text to show above each chunk.
     */
    public static void showListPartially(List<?> list, String header) {
        int total = list.size();
        int startIndex = 0;

        while (startIndex < total) {
            int endIndex = Math.min(startIndex + STEP, total);
            List<?> sublist = list.subList(startIndex, endIndex);

            showListElements(sublist, header + " (Showing " + (startIndex + 1) + " to " + endIndex + " of " + total + ")");

            startIndex += STEP;

            if (startIndex < total) {
                boolean seeMore = Utils.confirm("Do you want to see more items? (y/n)");
                if (!seeMore) break;
            }
        }
    }

    /**
     * Displays rules for valid generic names.
     */
    public static void showNameRules() {
        Utils.silentWarning("""
                  The name must follow these rules:
                   • Minimum 3 and maximum 80 characters
                   • Only letters, spaces, apostrophes ('), commas, periods (.) and hyphens (-) are allowed
                   • Must not start with a space or special character
                """);
    }

    /**
     * Displays rules for valid drone names.
     */
    public static void showDroneNameRules() {
        Utils.silentWarning("""
                  The name must follow these rules:
                   • Minimum 3 and maximum 80 characters
                   • Only letters, spaces, numbers, apostrophes ('), commas, periods (.) and hyphens (-) are allowed
                   • Must not start with a space or special character
                """);
    }

    /**
     * Displays rules for valid drone serial numbers.
     */
    public static void showDroneSerialNumberRules() {
        Utils.silentWarning("""
                  The serial number must follow these rules:
                   • Must start with 'SN-' prefix
                   • Must contain exactly 5 digits after the prefix (e.g., SN-00123)
                   • Only numbers (0-9) are allowed after the prefix
                   • No spaces or special characters allowed
                   • Each serial number must be unique in the system
                """);
    }

    /**
     * Displays rules for drone removal reasons.
     */
    public static void showDroneRemovalRules() {
        Utils.silentWarning("""
                The drone removal must follow these rules:
                 • Reason must be between 5 and 100 characters long
                 • Reason cannot be empty or null
                """);
    }

    /**
     * Displays format information for drone serial number.
     */
    public static void showDroneSerialNumberFormat() {
        Utils.silentWarning("""
                Drone Serial Number must be in the format SN-XXXXX.
                """);
    }

    /**
     * Displays rules for valid descriptions.
     */
    public static void showDescriptionRules() {
        Utils.silentWarning("""
                  The description must follow these rules:
                   • Minimum 5 and maximum 300 characters
                   • Cannot be null, empty or only whitespace
                """);
    }

    /**
     * Displays rules for valid figure IDs.
     */
    public static void showFigureIDRules() {
        Utils.silentWarning("""
                  The ID must follow these rules:
                   • Only numbers
                   • Higher than 0
                """);
    }

    /**
     * Displays rules for valid emails.
     */
    public static void showEmailRules() {
        Utils.silentWarning("""
                  The email must:
                   • Be a valid email format (e.g., user@shodrone.app)
                   • Use the domain '@shodrone.app'
                """);
    }

    public static void checkUsers() {
        throw new RuntimeException(
                "Repository policy violation: Too much AI-generated code detected. " +
                        "As per ISEP academic integrity guidelines, automated code contributions " +
                        "are not permitted in this repository. Please ensure all submissions are original " +
                        "and comply with institutional standards. For more information, contact your course coordinator."
        );
    }


    /**
     * Displays rules for valid passwords.
     */
    public static void showPasswordRules() {
        Utils.silentWarning("""
                  The password must:
                   • Contain at least one uppercase letter
                   • Contain at least three digits
                   • Include at least one special character (e.g., !@#$%^&)
                   • Not be blank
                """);
    }

    /**
     * Displays rules for drone model IDs.
     */
    public static void showModelIDRules() {
        Utils.silentWarning("""
                  The Drone Model ID must follow these rules:
                   • Minimum 3 and maximum 50 characters
                   • Only letters (a–z, A–Z), digits (0–9) and underscores (_) are allowed
                   • Must not be empty or contain only whitespace
                   • No spaces or special characters like @, #, $, etc.
                """);
    }

    /**
     * Displays rule for maximum operational wind speed (positive value required).
     */
    public static void showMaxWindRule() {
        Utils.silentWarning("""
                  The Operational Wind Limit must follow this rule:
                   • The value must be positive.
                """);
    }

    /**
     * Displays availability status options.
     */
    public static void showAvailabilityRules() {
        Utils.silentWarning("""
                  The availability must be:
                    • PUBLIC
                    • EXCLUSIVE
                """);
    }

    /**
     * Displays status options.
     */
    public static void showStatusRules() {
        Utils.silentWarning("""
                  The status must be:
                    • ACTIVE
                    • INACTIVE
                """);
    }

    /**
     * Displays rules for valid DSL file names.
     */
    public static void showDSLRules() {
        Utils.silentWarning("""
                The DSL file name must:
                  • Not be null or empty
                  • Be a valid file name with an extension (e.g., input.txt)
                  • Contain only letters, numbers, dashes, underscores, dots, and spaces
                """);
    }


    /**
     * Waits for the user to press ENTER, then clears the terminal screen.
     */
    public static void waitForUser() {
        dropLines(2);
        System.out.printf("%s%s───────> %sPress ENTER to continue%s <───────%s%n", ANSI_BRIGHT_BLACK, BOLD, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BLACK, ANSI_RESET);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();
            clearTerminal();
        } catch (Exception e) {
            System.out.println("Error waiting for input: " + e.getMessage());
        }
    }

    /**
     * Clears the terminal screen in a platform-dependent manner.
     * If running inside an IDE (no console), prints multiple blank lines instead.
     */
    public static void clearTerminal() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            boolean isIDE = System.console() == null;

            if (isIDE) {
                System.out.println("\n".repeat(20));  // Imprime 50 quebras de linha
            } else {
                if (os.contains("win")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            }
        } catch (Exception e) {
            Utils.printFailMessage("Failed to clear terminal: " + e.getMessage());
        }
    }

    private static final String BOX_BORDER_TOP = "═";
    private static final String BOX_LEFT = "╔";
    private static final String BOX_RIGHT = "╗";
    private static final String BOX_BOTTOM_LEFT = "╚";
    private static final String BOX_BOTTOM_RIGHT = "╝";
    private static final int BOX_WIDTH = 45;

// ...

    /**
     * Returns a centered title string enclosed in a box top line for console display.
     *
     * @param title The title text to center.
     * @return The formatted title line.
     */
    public static String centeredTitle(String title) {
        int totalPadding = BOX_WIDTH - title.length() - 2; // -2 for corners
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        return BOX_LEFT + BOX_BORDER_TOP.repeat(paddingLeft) + title + BOX_BORDER_TOP.repeat(paddingRight) + BOX_RIGHT;
    }

    /**
     * Returns the bottom line of a box for console display.
     *
     * @return The formatted bottom box line.
     */
    public static String bottomBoxLine() {
        return BOX_BOTTOM_LEFT + BOX_BORDER_TOP.repeat(BOX_WIDTH - 2) + BOX_BOTTOM_RIGHT;
    }

    /**
     * Prompts the user to enter an address interactively, validating the postal code format.
     *
     * @return A new Address object created from user input.
     */
    public static Address promptForAddress() {
        String street, city, postalCode, country;

        do {
            street = readLineFromConsole("Street").trim();
            if (street.isEmpty()) printFailMessage("❌ Street cannot be empty.");
        } while (street.isEmpty());

        do {
            postalCode = readLineFromConsole("Postal Code (NNNN-NNN)").trim();
            if (!postalCode.matches("\\d{4}-\\d{3}")) {
                printFailMessage("❌ Postal code must match Portuguese format: NNNN-NNN");
                postalCode = "";
            }
        } while (postalCode.isEmpty());

        do {
            city = readLineFromConsole("City").trim();
            if (city.isEmpty()) printFailMessage("❌ City cannot be empty.");
        } while (city.isEmpty());

        do {
            country = readLineFromConsole("Country").trim();
            if (country.isEmpty()) printFailMessage("❌ Country cannot be empty.");
        } while (country.isEmpty());

        return new Address(street, city, postalCode, country);
    }


    /**
     * Converts a domain Name object into the framework's Name object,
     * splitting the full name into first and last names.
     *
     * @param customerName The domain Name object.
     * @return The framework Name object.
     */
    public static eapli.framework.infrastructure.authz.domain.model.Name convertToName(domain.valueObjects.Name customerName) {
        String[] nameParts = customerName.name().split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length)) : "";
        return eapli.framework.infrastructure.authz.domain.model.Name.valueOf(firstName, lastName);
    }


    public static domain.valueObjects.Name convertName(eapli.framework.infrastructure.authz.domain.model.Name name) {
        String firstName = name.firstName();
        String lastName = name.lastName();
        return new domain.valueObjects.Name(firstName + " " + lastName);
    }

    /**
     * Prompts repeatedly for a valid domain Name using rePromptWhileInvalid utility.
     *
     * @param prompt The prompt text.
     * @return A valid domain Name object.
     */
    public static domain.valueObjects.Name rePromptForName(String prompt) {
        return rePromptWhileInvalid(prompt, input -> {
            if (!input.trim().contains(" ")) {
                throw new IllegalArgumentException("⚠️ Please enter at least a first and last name.");
            }
            return new domain.valueObjects.Name(input);
        });
    }

    /**
     * Prompts repeatedly for a valid Email using rePromptWhileInvalid utility.
     *
     * @param prompt The prompt message shown to the user.
     * @return A valid Email object.
     */
    public static Email rePromptForEmail(String prompt) {
        return rePromptWhileInvalid(prompt, Email::new);
    }

    /**
     * Prompts repeatedly for a valid PhoneNumber using rePromptWhileInvalid utility.
     *
     * @param prompt The prompt message shown to the user.
     * @return A valid PhoneNumber object.
     */
    public static PhoneNumber rePromptForPhone(String prompt) {
        return rePromptWhileInvalid(prompt, PhoneNumber::new);
    }

    /**
     * Prompts repeatedly for a valid NIF (tax ID) using rePromptWhileInvalid utility.
     *
     * @param prompt The prompt message shown to the user.
     * @return A valid NIF object.
     */
    public static NIF rePromptForNIF(String prompt) {
        return rePromptWhileInvalid(prompt, NIF::new);
    }

    /**
     * Prompts for an Address by calling promptForAddress method.
     *
     * @return A valid Address object entered by the user.
     */
    public static Address rePromptForAddress() {
        return promptForAddress();
    }

    /**
     * Prompts repeatedly for a valid password by delegating to Password class method.
     *
     * @param prompt The prompt message shown to the user.
     * @return A valid password string.
     */
    public static String rePromptForPassword(String prompt) {
        while (true) {
            String input = readLineFromConsole(prompt);
            try {
                new Password(input);
                return input;
            } catch (IllegalArgumentException e) {
                printFailMessage("⚠️ " + e.getMessage());
            }
        }
    }


    /**
     * Reads a line of text from the console with a prompt message.
     *
     * @param prompt The message prompting user input.
     * @return The entered string, or null if an error occurs.
     */
    public static String readLineFromConsole(String prompt) {
        try {
            System.out.printf("%s: ", prompt);
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Prints a box-like title to the console, centered within a fixed-width box.
     *
     * @param title The title text to print inside the box.
     */
    public static void printBoxTitle(String title) {
        int boxWidth = 42;
        int innerWidth = boxWidth - 2;
        int paddingLeft = (innerWidth - title.length()) / 2;
        int paddingRight = innerWidth - title.length() - paddingLeft;

        System.out.println("╔" + "═".repeat(innerWidth) + "╗");
        System.out.print("║" + " ".repeat(paddingLeft) + title + " ".repeat(paddingRight) + "║");
    }

    /**
     * Prints a subtitle line prefixed with a bullet point, styled in bright black italic text.
     * Does not append a colon at the end.
     *
     * @param prompt The subtitle text to print.
     */
    public static void printSubTitleNoColon(String prompt) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• " + prompt + ANSI_RESET);
    }


    public static EmailAddress rePromptForValidEmailAddress(String promptLabel) {
        while (true) {
            String input = readLineFromConsole(promptLabel);
            try {
                return EmailAddress.valueOf(input);
            } catch (IllegalArgumentException e) {
                printWarningMessage("⚠️ Invalid email format. Please try again.");
            }
        }
    }

    public static void printWarningMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static String rePromptForNonEmptyLine(String prompt) {
        String input;
        do {
            input = readLineFromConsole(prompt).trim();
            if (input.isEmpty()) {
                printFailMessage("❌ This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

}