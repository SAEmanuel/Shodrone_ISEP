package controller;

import domain.entity.Costumer;
import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.interfaces.ShowRequestRepository;
import ui.FoundCostumerUI;
import ui.ListFiguresByCostumerUI;

import java.util.List;
import java.util.Optional;

public class RegisterShowRequestController {
    private final ShowRequestRepository showRequestRepository;

    private final FoundCostumerUI foundCostumerUI;
    private final ListFiguresByCostumerUI listFiguresByCostumerUI;

    private Costumer costumerSelected;
    private List<Figure> figuresSelected;

    public RegisterShowRequestController() {
        this.showRequestRepository = RepositoryProvider.showRequestRepository();
        this.foundCostumerUI = new FoundCostumerUI();
        this.listFiguresByCostumerUI = new ListFiguresByCostumerUI();
        this.costumerSelected = null;
    }

    public void registerShowRequest(){}

    public void foundCostumerForRegistration(){
        Optional<Costumer> result = foundCostumerUI.foundCustomersUI();
        if(result.isEmpty()){
            throw new IllegalArgumentException("No customer selected.");
        }
        costumerSelected = result.get();
    }
    //TODO
    public void foundFiguresForRegistration(){}
}
