package ui;

import controller.ListFiguresByCostumerController;
import domain.entity.Costumer;
import domain.entity.Figure;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Figure> figureList = getListFiguresByCostumerController().listFiguresByCostumer(costumer).orElse(new ArrayList<>());

        while (!figureList.isEmpty()) {
            Optional<Figure> selected = (Optional<Figure>) Utils.showAndSelectObjectFromList(Optional.of(figureList), "Figures");
            if (selected.isEmpty()) {
                break;
            }
            Figure figure = selected.get();
            selectedFigureList.add(figure);
            figureList.remove(figure);
        }

        return selectedFigureList;
    }

}
