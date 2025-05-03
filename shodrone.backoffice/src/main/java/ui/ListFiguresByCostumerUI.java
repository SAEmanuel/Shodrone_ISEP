package ui;

import controller.ListFiguresByCostumerController;
import domain.entity.Costumer;
import domain.entity.Figure;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

public class ListFiguresByCostumerUI implements Runnable {
    private final ListFiguresByCostumerController listFiguresByCostumerController;

    public ListFiguresByCostumerUI() {
        listFiguresByCostumerController = new ListFiguresByCostumerController();
    }
    private ListFiguresByCostumerController getListFiguresByCostumerController() {
        return listFiguresByCostumerController;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("LIST FIGURES BY COSTUMER");
        try {
            //getListFiguresUI();
        }catch(Exception e){
            Utils.printAlterMessage(e.getMessage());
        }
    }

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
                    System.out.printf("    %s(%d)%s -  %sID: %-3d | Name: %-10s | Availability: %s%s%n",
                            ANSI_BRIGHT_BLACK, i + 1, ANSI_RESET, ANSI_BRIGHT_WHITE,
                            selectedFigure.identity(), selectedFigure.name(), selectedFigure.availability(), ANSI_RESET);
                }
            }

            Utils.dropLines(1);

            // Mostrar lista numerada com opção 0 para sair
            System.out.println(ANSI_BRIGHT_BLACK + BOLD +"• Figures:"+ ANSI_RESET);
            System.out.printf("    %s(0)%s -  Finish selection%n", ANSI_BRIGHT_BLACK, ANSI_RESET);
            for (int i = 0; i < figureList.size(); i++) {
                Figure f = figureList.get(i);
                System.out.printf("    %s(%d)%s -  ID: %-3d | Name: %-10s | Availability: %s%n",
                        ANSI_BRIGHT_BLACK,i + 1,ANSI_RESET, f.identity(), f.name(), f.availability());
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

            System.out.printf("%s✔ Added ->%s ID: %-3d | Name: %-10s%s%n",
                    ANSI_GREEN, ANSI_BRIGHT_BLACK, selectedFigure.identity(),
                    selectedFigure.name(), ANSI_RESET);

            Utils.dropLines(2);
        }

        return selectedFigureList;
    }



}
