package controller;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.valueObjects.Location;
import factories.FactoryProvider;
import domain.valueObjects.Description;
import persistence.RepositoryProvider;
import persistence.interfaces.ShowRequestRepository;
import ui.FoundCostumerUI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RegisterShowRequestController {
    private final ShowRequestRepository showRequestRepository;
    private final FoundCostumerUI foundCostumerUI;

    private Costumer costumerSelected;
    private List<Figure> figuresSelected;
    private Description descriptionOfShowRequest;
    private Location locationOfShow;
    private LocalDateTime showDate;
    private int numberOfDrones;
    private int showDuration;

    public RegisterShowRequestController() {
        this.showRequestRepository = RepositoryProvider.showRequestRepository();
        this.foundCostumerUI = new FoundCostumerUI();

        this.costumerSelected = null;
        this.figuresSelected = null;
    }

    public void foundCostumerForRegistration(){
        Optional<Costumer> result = foundCostumerUI.foundCustomersUI();
        if(result.isEmpty()){
            throw new IllegalArgumentException("No customer selected.");
        }
        costumerSelected = result.get();
    }
    public void foundFiguresForRegistration(){}
    public void getDescriptionsForRegistration(String rawDescriptionOfShowRequest){
        descriptionOfShowRequest = new Description(rawDescriptionOfShowRequest);
    }
    public void getLocationOfShow(){this.locationOfShow = FactoryProvider.getLocationFactoryImpl().createLocationObject();}
}
