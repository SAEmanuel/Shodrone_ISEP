package ui.showrequest;

import controller.showrequest.ListFiguresByCostumerController;
import domain.entity.Costumer;
import domain.entity.Figure;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

/**
 * UI class responsible for listing and selecting figures associated with a given customer.
 * <p>
 * Provides an interactive console-based interface that allows users to select one or more
 * figures from a customer's list of available figures.
 */
public class ListFiguresByCostumerUI implements Runnable {

    /** Controller responsible for retrieving figures for a given customer. */
    private final ListFiguresByCostumerController listFiguresByCostumerController;

    /**
     * Constructs the UI with a new instance of {@link ListFiguresByCostumerController}.
     */
    public ListFiguresByCostumerUI() {
        listFiguresByCostumerController = new ListFiguresByCostumerController();
    }

    /**
     * Returns the controller used to fetch figures related to a customer.
     *
     * @return the {@link ListFiguresByCostumerController} instance
     */
    private ListFiguresByCostumerController getListFiguresByCostumerController() {
        return listFiguresByCostumerController;
    }

    /**
     * Entry point for the UI. Currently prints a title and handles exceptions.
     * The main logic resides in {@link #getListFiguresUI(Costumer)} which requires a {@link Costumer}.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("LIST FIGURES BY COSTUMER");
        try {
            // Placeholder for actual logic using getListFiguresUI().
        } catch(Exception e){
            Utils.printAlterMessage(e.getMessage());
        }
    }

    /**
     * Allows the user to interactively select figures associated with the given customer.
     *
     * <p>The figures already selected are shown above the remaining options, and the user can
     * choose one by typing its number. Selected figures are added to a list and removed from
     * the options. The user can finish the selection by choosing option 0 or pressing ENTER.</p>
     *
     * @param costumer the customer whose figures should be listed and selected from
     * @return a list of {@link Figure} objects selected by the user
     */
    public List<Figure> getListFiguresUI(Costumer costumer) {
        List<Figure> selectedFigureList = new ArrayList<>();
        List<Figure> figureList = getListFiguresByCostumerController()
                .listFiguresByCostumer(costumer)
                .orElse(new ArrayList<>());

        if (figureList.isEmpty()) {
            Utils.printAlterMessage("No figures available for this customer.");
            return selectedFigureList;
        }

        while (!figureList.isEmpty()) {
            System.out.println("\n--- Select figures from the list below ---");
            System.out.println(ANSI_BRIGHT_BLACK + BOLD + "• Currently selected figures:" + ANSI_RESET);

            if (selectedFigureList.isEmpty()) {
                System.out.printf("   %s[%sNone selected yet%s]%s%n",
                        ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BLACK, ANSI_RESET);
            } else {
                for (int i = 0; i < selectedFigureList.size(); i++) {
                    Figure selectedFigure = selectedFigureList.get(i);
                    System.out.printf("    %s(%-2d)%s -  ID: %-3d | Name: %-15s | Availability: %-9s%s%n",
                            ANSI_BRIGHT_BLACK, i + 1, ANSI_RESET,
                            selectedFigure.identity(), selectedFigure.name(), selectedFigure.availability(), ANSI_RESET);
                }
            }

            Utils.dropLines(1);

            System.out.println(ANSI_BRIGHT_BLACK + BOLD + "• Figures:" + ANSI_RESET);
            System.out.printf("    %s(0)%s  -  Finish selection%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
            for (int i = 0; i < figureList.size(); i++) {
                Figure f = figureList.get(i);
                System.out.printf("    %s(%-2d)%s -  ID: %-3d | Costumer: %-18s | Name: %-15s | Availability: %-9s%s%n",
                        ANSI_BRIGHT_BLACK, i + 1, ANSI_RESET,
                        f.identity(), f.costumer().name(), f.name(), f.availability(), ANSI_RESET);
            }

            String input = Utils.readLineFromConsole("Select a figure by number (or press ENTER to finish)").trim();

            if (input.isEmpty()) {
                Utils.printAlterMessage("Selection terminated.");
                break;
            }

            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Utils.printAlterMessage("Invalid input. Please enter a valid number.");
                continue;
            }

            if (option == 0) {
                Utils.printAlterMessage("Selection terminated by user.");
                break;
            }

            if (option < 1 || option > figureList.size()) {
                Utils.printAlterMessage("Invalid option. Try again.");
                continue;
            }

            Figure selectedFigure = figureList.get(option - 1);
            selectedFigureList.add(selectedFigure);
            figureList.remove(selectedFigure);

            System.out.printf("%s✔ Added ->%s ID: %-3d | Name: %-15s%s%n",
                    ANSI_GREEN, ANSI_BRIGHT_BLACK,
                    selectedFigure.identity(), selectedFigure.name(), ANSI_RESET);

            Utils.dropLines(2);
        }

        return selectedFigureList;
    }
}
