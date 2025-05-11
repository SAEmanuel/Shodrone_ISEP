package ui.common;

import utils.Utils;
import java.util.Scanner;
import static more.ColorfulOutput.*;

/**
 * UI class that allows the user to select the type of persistence to be used in the application.
 *
 * <p>It offers two options:
 * <ul>
 *     <li><b>In Memory</b>: Stores data temporarily in memory (e.g., file-based storage).</li>
 *     <li><b>Database</b>: Persists data using a relational database (e.g., H2).</li>
 * </ul>
 *
 * <p>The selection is made through a command-line interface where the user inputs either 1 or 2.</p>
 */
public class PersistenceSelectorUI {

    /**
     * Enumeration representing the supported types of persistence.
     */
    public enum PersistenceType {
        /** In-memory persistence. */
        MEMORY,
        /** Persistent storage using a relational database. */
        DATABASE
    }

    /**
     * Default constructor for {@code PersistenceSelectorUI}.
     */
    public PersistenceSelectorUI() {
    }

    /**
     * Displays the persistence selection menu and handles user input.
     *
     * <p>The method ensures the user selects a valid option (1 or 2),
     * clears the terminal after selection, and returns the corresponding integer value.</p>
     *
     * @return the numeric value of the selected option:
     *         <ul>
     *             <li>1 for {@code MEMORY}</li>
     *             <li>2 for {@code DATABASE}</li>
     *         </ul>
     */
    public int selectionUI() {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s╔═════%sSELECT THE TYPE OF PERSISTENCE%s════╗%s%n",ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s╚═══════════════════════════════════════╝%s%n",ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("    %s(1)%s -  In Memory  %n",ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("    %s(2)%s -  In Database (H2)%n",ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s═════════════════════════════════════════%s%n",ANSI_BRIGHT_BLACK, ANSI_RESET);


        int option = -1;
        while (true) {
            System.out.print("Type option (1 or 2): ");
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);

                if (option == 1 || option == 2) {
                    break;
                } else {
                    Utils.printFailMessage("Invalid option. Please choose 1 or 2.");
                }
            } catch (NumberFormatException e) {
                Utils.printFailMessage("Input is not a valid number.");
            }
        }

        PersistenceType selectedType = switch (option) {
            case 1 -> PersistenceType.MEMORY;
            case 2 -> PersistenceType.DATABASE;
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };

        Utils.clearTerminal();

        return option;
    }


}
