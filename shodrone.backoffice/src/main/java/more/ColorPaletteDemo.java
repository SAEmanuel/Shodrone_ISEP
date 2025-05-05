package more;

import static more.ColorfulOutput.ANSI_TEAL;

public class ColorPaletteDemo {
    public static void main(String[] args) {
        System.out.println("\n\033[1mColor Palette Preview:\033[0m\n");

        printColor("BLACK", ColorfulOutput.ANSI_BLACK);
        printColor("YELLOW", ColorfulOutput.ANSI_YELLOW);
        printColor("BLUE", ColorfulOutput.ANSI_BLUE);
        printColor("PURPLE", ColorfulOutput.ANSI_PURPLE);
        printColor("CYAN", ColorfulOutput.ANSI_CYAN);
        printColor("WHITE", ColorfulOutput.ANSI_WHITE);
        printColor("BRIGHT BLACK", ColorfulOutput.ANSI_BRIGHT_BLACK);
        printColor("BRIGHT YELLOW", ColorfulOutput.ANSI_BRIGHT_YELLOW);
        printColor("BRIGHT BLUE", ColorfulOutput.ANSI_BRIGHT_BLUE);
        printColor("BRIGHT PURPLE", ColorfulOutput.ANSI_BRIGHT_PURPLE);
        printColor("BRIGHT CYAN", ColorfulOutput.ANSI_BRIGHT_CYAN);
        printColor("BRIGHT WHITE", ColorfulOutput.ANSI_BRIGHT_WHITE);

        System.out.println("\n\033[1mGreens:\033[0m");
        printColor("GREEN", ColorfulOutput.ANSI_GREEN);
        printColor("BRIGHT GREEN", ColorfulOutput.ANSI_BRIGHT_GREEN);
        printColor("LIGHT GREEN", ColorfulOutput.ANSI_LIGHT_GREEN);
        printColor("PALE GREEN", ColorfulOutput.ANSI_PALE_GREEN);
        printColor("SEA GREEN", ColorfulOutput.ANSI_SEA_GREEN);
        printColor("FOREST GREEN", ColorfulOutput.ANSI_FOREST_GREEN);
        printColor("SPRING GREEN", ColorfulOutput.ANSI_SPRING_GREEN);
        printColor("LIME GREEN", ColorfulOutput.ANSI_LIME_GREEN);
        printColor("GREEN YELLOW", ColorfulOutput.ANSI_GREEN_YELLOW);
        printColor("MEDIUM SPRING GREEN", ColorfulOutput.ANSI_MEDIUM_SPRING_GREEN);

        System.out.println("\n\033[1mReds & Oranges:\033[0m");
        printColor("RED", ColorfulOutput.ANSI_RED);
        printColor("BRIGHT RED", ColorfulOutput.ANSI_BRIGHT_RED);
        printColor("LIGHT RED", ColorfulOutput.ANSI_LIGHT_RED);
        printColor("DARK RED", ColorfulOutput.ANSI_DARK_RED);
        printColor("PINK", ColorfulOutput.ANSI_PINK);
        printColor("CRIMSON", ColorfulOutput.ANSI_CRIMSON);
        printColor("SALMON", ColorfulOutput.ANSI_SALMON);
        printColor("FIREBRICK", ColorfulOutput.ANSI_FIREBRICK);
        printColor("INDIAN RED", ColorfulOutput.ANSI_INDIAN_RED);
        printColor("MAROON", ColorfulOutput.ANSI_MAROON);
        printColor("ORANGE", ColorfulOutput.ANSI_ORANGE);
        printColor("DARK ORANGE", ColorfulOutput.ANSI_DARK_ORANGE);
        printColor("LIGHT ORANGE", ColorfulOutput.ANSI_LIGHT_ORANGE);
        printColor("CORAL", ColorfulOutput.ANSI_CORAL);
        printColor("TOMATO", ColorfulOutput.ANSI_TOMATO);
        printColor("ORANGE RED", ColorfulOutput.ANSI_ORANGE_RED);
        printColor("ANSI_TEAL", ColorfulOutput.ANSI_TEAL);
        printColor("ANSI_NAVY", ColorfulOutput.ANSI_NAVY);
        printColor("ANSI_VIOLET", ColorfulOutput.ANSI_VIOLET);
        printColor("ANSI_GOLD", ColorfulOutput.ANSI_GOLD);
        printColor("ANSI_OLIVE", ColorfulOutput.ANSI_OLIVE);
        printColor("ANSI_SKY_BLUE", ColorfulOutput.ANSI_SKY_BLUE);
        printColor("ANSI_ROSE", ColorfulOutput.ANSI_ROSE);
        printColor("ANSI_GRAY", ColorfulOutput.ANSI_GRAY);

        System.out.println();
    }

    private static void printColor(String name, String ansiCode) {
        System.out.println(ansiCode + name + ColorfulOutput.ANSI_RESET);
    }
}
