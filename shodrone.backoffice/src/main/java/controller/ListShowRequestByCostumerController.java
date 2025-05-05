package controller;

import domain.entity.Costumer;
import domain.entity.ShowRequest;
import persistence.RepositoryProvider;
import ui.FoundCostumerUI;

import java.util.List;
import java.util.Optional;

public class ListShowRequestByCostumerController {
    private final FoundCostumerUI foundCostumerUI;
    private Costumer costumerSelected;

    public ListShowRequestByCostumerController() {
        this.foundCostumerUI = new FoundCostumerUI();
    }

    public List<ShowRequest> listShowRequestByCostumer() {
        foundCostumerForRegistration();

        Optional<List<ShowRequest>> showRequestList = RepositoryProvider.showRequestRepository().findByCostumer(costumerSelected);

        if (showRequestList.isPresent()) {
            return showRequestList.get();
        } else {
            throw new IllegalArgumentException("No show requests found for the given costumer.");
        }
    }


    private void foundCostumerForRegistration(){
        Optional<Costumer> result = foundCostumerUI.foundCustomersUI();
        if(result.isEmpty()){
            throw new IllegalArgumentException("No customer selected.");
        }
        costumerSelected = result.get();
    }

}
