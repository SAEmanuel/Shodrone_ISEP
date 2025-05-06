package utils;


import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.NIF;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static more.ColorfulOutput.*;
import static more.TextEffects.*;


public class Utils {
    final static String COLOR_OPTIONS = ANSI_BRIGHT_BLACK;
    final static int STEP = 10;

    public static void exitImmediately(String message) {
        System.out.printf("%n%s%s%s%n%n", ANSI_BRIGHT_RED, message, ANSI_RESET);
        throw new RuntimeException("Exiting application");
    }

    public static void dropLines(int nLines) {
        if (nLines > 0) {
            System.out.print("\n".repeat(nLines));
        }
    }

    public static void printOptionalValidMessage(String message, Optional<?> optionalMessage) {
        if (message != null && !message.isEmpty()) {
            System.out.printf("%s%s: %s%s%n", ANSI_GREEN, message, optionalMessage.get().toString(), ANSI_RESET);
        }
    }

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

    public static void printCenteredSubtitle(String subtitle) {
        int lineLength = 30;

        // Linha superior decorativa
        System.out.print("\n"+ANSI_BRIGHT_BLACK);
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
        System.out.println(ANSI_RESET+"\n");
    }

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



    static public void printSubTitle(String prompt) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(prompt).concat(ANSI_RESET).concat(":"));
    }

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.printf("%s: ", prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    static public int readPositiveIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                if (value < 0) {
                    printFailMessage("Invalid number. Please enter a positive integer value.");
                    System.out.println();
                    continue;
                }

                return value;
            } catch (NumberFormatException ex) {
                printFailMessage("Invalid number. Please enter a valid integer value.");
                System.out.println();
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                printAlterMessage("Invalid number. Please enter a valid decimal number.");
            }
        } while (true);
    }


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

    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message);
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("y");
    }

    static public Object showAndSelectOne(List<?> list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    static public int showAndSelectIndex(List<?> list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public int showAndSelectIndexBigger(List<?> list, String header) {
        showListBigger(list, header);
        return selectsIndex(list);
    }

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

    static public int showAndSelectIndexCustomOptions(List<?> list, String header) {
        showListCustom(list, header);
        return selectsIndex(list);
    }

    static public int showAndSelectIndexDataBase(List<?> list, String header) {
        showListDataBase(list, header);
        return selectsIndex(list);
    }

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

    static public void showPagedElementList(List<?> list, int sizeOfObjectsPerPage, String header) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil( (double) totalItems / sizeOfObjectsPerPage);

        int lineLength = 30;
        for (int page = 0; page <= totalPages; page++) {

            if (page <= totalPages){
                Utils.dropLines(2);
                Utils.printCenteredSubtitle(header);
                System.out.printf("%s│    Page (%d)  │%s%n", COLOR_OPTIONS,(page),ANSI_RESET);
            }

            int startIndex = (page - 1) * sizeOfObjectsPerPage;
            int endIndex = Math.min(startIndex + sizeOfObjectsPerPage, totalItems);
            List<?> pageItems = list.subList(startIndex, endIndex);

            for (Object o : pageItems) {
                System.out.printf(COLOR_OPTIONS + "│" + ANSI_RESET + "%sFigure -> %-28s%s%n", ANSI_BLUE, o.toString(), ANSI_RESET );
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

            if(input.equalsIgnoreCase("n")) { break; }
        }
    }

    static public void showPagedElementListByStartingPage(List<?> list, int startingPage, int sizeOfObjectsPerPage, String header) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil( (double) totalItems / sizeOfObjectsPerPage);

        if (startingPage < 1 || startingPage > totalPages) {
            Utils.printFailMessage("No elements found starting at page " + startingPage + " with " + sizeOfObjectsPerPage + " per page -> total list size : " + list.size());
            return;
        }

        int lineLength = 30;

        for (int page = startingPage; page <= totalPages; page++) {

            if (page <= totalPages){
                Utils.dropLines(2);
                Utils.printCenteredSubtitle(header);
                System.out.printf("%s│    Page (%d)  │%s%n", COLOR_OPTIONS,(page),ANSI_RESET);
            }

            int startIndex = (page - 1) * sizeOfObjectsPerPage;
            int endIndex = Math.min(startIndex + sizeOfObjectsPerPage, totalItems);
            List<?> pageItems = list.subList(startIndex, endIndex);

            for (Object o : pageItems) {
                System.out.printf(COLOR_OPTIONS + "│" + ANSI_RESET + "%sFigure -> %-28s%s%n", ANSI_BLUE, o.toString(), ANSI_RESET );
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


            if(input.equalsIgnoreCase("n")) { break; }
        }
    }

    static public void printAlterMessage(String message) {
        System.out.println(ANSI_ORANGE.concat(BOLD).concat(message).concat(ANSI_RESET));
    }

    static public void silentWaring(String message) {
        System.out.println(COLOR_OPTIONS + message + ANSI_RESET);
    }

    static public void showListCustom(List<?> list, String header) {
        System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(header).concat(":") + ANSI_RESET);
        int index = 0;
        for (Object o : list) {
            index++;
            System.out.printf("    %s(%d)%s -  %-28s%n", COLOR_OPTIONS, index, ANSI_RESET, o.toString());
        }
        System.out.printf("    %s(0)%s -  %-20s%n", COLOR_OPTIONS, ANSI_RESET, "Cancel");
    }

    static public void showListElements(List<?> list, String header) {
        if (!header.isEmpty())
            System.out.println(ANSI_BRIGHT_BLACK + ITALIC + "• ".concat(header).concat(":") + ANSI_RESET);

        for (Object o : list) {
            System.out.printf("    %s  %s%-2s%s%n", "•", ANSI_BLUE, o.toString(), ANSI_RESET);
        }

        dropLines(3);
    }

    static public Object selectsObject(List<?> list) {
        String input;
        int value;
        do {
            input = Utils.readLineFromConsole("Type your option");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

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
        } while (value < 0 || value > list.size());
        //dropLines(1);
        return (value - 1) + (cycle * STEP);
    }

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

    public static void printSuccessMessage(String message) {
        System.out.println(ANSI_BRIGHT_GREEN + message + ANSI_RESET);
    }

    public static void printFailMessage(String message) {
        System.out.println(ANSI_BRIGHT_RED + message + ANSI_RESET);
    }

    public static Optional<?> showAndSelectObjectFromList(Optional<List<?>> optionalResult, String header) {
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
                    System.out.println(cycle);
                    continue;
                }
            }

            return selectsIndex(list, cycle);
        }

        return -1;
    }

    public static void showNameRules() {
        Utils.silentWaring("""
              The name must follow these rules:
               • Minimum 3 and maximum 80 characters
               • Only letters, spaces, apostrophes ('), commas, periods (.) and hyphens (-) are allowed
               • Must not start with a space or special character
            """);
    }

    public static void showDescriptionRules() {
        Utils.silentWaring("""
          The description must follow these rules:
           • Minimum 5 and maximum 300 characters
           • Cannot be null, empty or only whitespace
        """);
    }

    public static void showEmailRules() {
        Utils.silentWaring("""
              The email must:
               • Be a valid email format (e.g., user@shodrone.app)
               • Use the domain '@shodrone.app'
            """);
    }

    public static void showPasswordRules() {
        Utils.silentWaring("""
              The password must:
               • Contain at least one uppercase letter
               • Contain at least three digits
               • Include at least one special character (e.g., !@#$%^&)
               • Not be blank
            """);
    }

    public static void showModelIDRules() {
        Utils.silentWaring("""
          The Drone Model ID must follow these rules:
           • Minimum 3 and maximum 50 characters
           • Only letters (a–z, A–Z), digits (0–9) and underscores (_) are allowed
           • Must not be empty or contain only whitespace
           • No spaces or special characters like @, #, $, etc.
        """);
    }

    public static void showMaxWindRule() {
        Utils.silentWaring("""
          The Operational Wind Limit must follow this rule:
           • The value must be positive.
        """);
    }

    public static void waitForUser() {
        dropLines(2);
        System.out.printf("%s%s───────> %sPress ENTER to continue%s <───────%s%n",ANSI_BRIGHT_BLACK,BOLD,ANSI_BRIGHT_WHITE,ANSI_BRIGHT_BLACK,ANSI_RESET);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();
            clearTerminal();
        } catch (Exception e) {
            System.out.println("Error waiting for input: " + e.getMessage());
        }
    }

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

    public static String centeredTitle(String title) {
        int totalPadding = BOX_WIDTH - title.length() - 2; // -2 for corners
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        return BOX_LEFT + BOX_BORDER_TOP.repeat(paddingLeft) + title + BOX_BORDER_TOP.repeat(paddingRight) + BOX_RIGHT;
    }

    public static String bottomBoxLine() {
        return BOX_BOTTOM_LEFT + BOX_BORDER_TOP.repeat(BOX_WIDTH - 2) + BOX_BOTTOM_RIGHT;
    }




}