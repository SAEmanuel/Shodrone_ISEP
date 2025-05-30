package controller.service;

import controller.showrequest.ListFiguresByCostumerController;
import domain.entity.Costumer;
import domain.entity.Figure;
import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static more.ColorfulOutput.*;
import static more.TextEffects.*;

public class ServiceForValidSequenceFiguresForShow {

    public static Queue<Figure> getListFiguresUIWithRepetitions(Costumer costumer, Queue<Figure> initialSelection) {
        ListFiguresByCostumerController listFiguresByCostumerController = new ListFiguresByCostumerController();
        Queue<Figure> selectedFigureQueue = new LinkedList<>(initialSelection != null ? initialSelection : new ArrayList<>());
        List<Figure> figureList = listFiguresByCostumerController
                .listFiguresByCostumer(costumer)
                .orElse(new ArrayList<>());

        if (figureList.isEmpty()) {
            Utils.printAlterMessage("No figures available for this customer.");
            return selectedFigureQueue;
        }

        Figure lastSelectedFigure = selectedFigureQueue.isEmpty() ? null : ((LinkedList<Figure>) selectedFigureQueue).getLast();

        while (true) {
            System.out.printf("\n%s─── Select figures from the list below ───%s%n", ANSI_BOLD, ANSI_RESET);
            System.out.println(ANSI_BRIGHT_BLACK + BOLD + "• Currently selected figures:" + ANSI_RESET);

            if (selectedFigureQueue.isEmpty()) {
                System.out.printf("    %s[%sNone selected yet%s]%s%n",
                        ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_BRIGHT_BLACK, ANSI_RESET);
            } else {
                for (Figure selectedFigure : selectedFigureQueue) {
                    String highlight = selectedFigure.equals(lastSelectedFigure) ? ANSI_BRIGHT_CYAN : ANSI_RESET;
                    System.out.printf("    🗳️ %sID: %-3d | Name: %-15s | Availability: %-9s%s%n",
                            highlight, selectedFigure.identity(), selectedFigure.name(), selectedFigure.availability(), ANSI_RESET);
                }
            }

            Utils.dropLines(1);

            System.out.println(ANSI_BRIGHT_BLACK + BOLD + "• Figures:" + ANSI_RESET);
            System.out.printf("    %s(-2)%s -  %sRemove last added figure%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_MEDIUM_SPRING_GREEN, ANSI_RESET); // NOVA OPÇÃO
            System.out.printf("    %s(-1)%s -  %sClear selected figures%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_MEDIUM_SPRING_GREEN, ANSI_RESET);
            System.out.printf("    %s(0)%s  -  %sFinish selection%s%n", ANSI_BRIGHT_BLACK, ANSI_RESET, ANSI_MEDIUM_SPRING_GREEN, ANSI_RESET);

            for (int i = 0; i < figureList.size(); i++) {
                Figure f = figureList.get(i);
                System.out.printf("    %s(%-3s%s -  ID: %-3d | Costumer: %-18s | Name: %-15s | Availability: %-9s%s%n",
                        ANSI_BRIGHT_BLACK, i + 1 + ")", ANSI_RESET,
                        f.identity(), f.costumer().name(), f.name(), f.availability(), ANSI_RESET);
            }

            String input = Utils.readLineFromConsole("Select a Figure").trim();

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

            if (option == -1) {
                Utils.printAlterMessage("Selected figures cleared. You can start selecting again.");
                selectedFigureQueue.clear();
                lastSelectedFigure = null;
                continue;
            }

            if (option == -2) {
                if (selectedFigureQueue.isEmpty()) {
                    Utils.printAlterMessage("No figures to remove.");
                } else {
                    LinkedList<Figure> tempList = (LinkedList<Figure>) selectedFigureQueue;
                    Figure removed = tempList.removeLast();
                    lastSelectedFigure = tempList.isEmpty() ? null : tempList.getLast();
                    Utils.printAlterMessage("Removed last selected figure: " + removed.name());
                }
                continue;
            }

            if (option < 1 || option > figureList.size()) {
                Utils.printAlterMessage("Invalid option. Try again.");
                continue;
            }

            Figure selectedFigure = figureList.get(option - 1);

            if (selectedFigure.equals(lastSelectedFigure)) {
                Utils.printAlterMessage("Cannot select the same figure consecutively. Choose a different figure.");
                continue;
            }

            selectedFigureQueue.add(selectedFigure);
            lastSelectedFigure = selectedFigure;

            System.out.printf("%s✔ Added ->%s ID: %-3d | Name: %-15s%s%n",
                    ANSI_GREEN, ANSI_RESET,
                    selectedFigure.identity(), selectedFigure.name(), ANSI_RESET);

            Utils.dropLines(4);
        }

        return selectedFigureQueue;
    }



}
