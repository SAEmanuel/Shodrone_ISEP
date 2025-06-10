package ui.drone;

import controller.drone.DroneOneProgramGeneratorController;
import utils.Utils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GenerateDroneCodeUI implements Runnable {

    private static final String DSL_FOLDER = "docs/planning/sprint3/DSL/";

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        DroneOneProgramGeneratorController generator = new DroneOneProgramGeneratorController();

        try {
            File folder = new File(DSL_FOLDER);
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (files == null || files.length == 0) {
                System.out.println("No DSL files found in folder: " + DSL_FOLDER);
                return;
            }

            while (true) {
                System.out.println("\nAvailable DSL files:");
                for (int i = 1; i <= files.length; i++) {
                    System.out.println(i + ": " + files[i - 1].getName());
                }
                System.out.println("0: Exit");

                System.out.print("Choose a file index (1-" + files.length + ", or 0 to exit): ");

                int choice = -1;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    scanner.next();
                    continue;
                }

                if (choice == 0) {
                    System.out.println("Exiting...");
                    return;
                }

                if (choice < 1 || choice > files.length) {
                    Utils.printFailMessage("Invalid choice. Please enter a number between 0 and " + files.length);
                    continue;
                }

                int fileIndex = choice - 1;
                String selectedFilePath = files[fileIndex].getAbsolutePath();
                System.out.println("You selected: " + files[fileIndex].getName());

                String dslContent = Files.readString(Paths.get(selectedFilePath));
                generator.generateDronePrograms(dslContent);

                break;
            }

        } catch (Exception e) {
            System.err.println("Error reading DSL file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
