package ui;

import utils.Utils;
import java.util.Scanner;
import static more.ColorfulOutput.*;

public class PersistenceSelectorUI {

    public enum PersistenceType {
        MEMORY, DATABASE
    }

    public PersistenceSelectorUI() {
    }

    public int selectionUI() {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s╔═════%sSELECT THE TYPE OF PERSISTENCE%s════╗%s%n",ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("%s╚═══════════════════════════════════════╝%s%n",ANSI_BRIGHT_BLACK, ANSI_RESET);
        System.out.printf("    %s(1)%s -  In Memory  (File)%n",ANSI_BRIGHT_BLACK, ANSI_RESET);
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
