package controller.showrequest;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import history.HistoryLogger;
import domain.valueObjects.Location;
import factories.FactoryProvider;
import domain.valueObjects.Description;
import persistence.RepositoryProvider;
import ui.customer.FoundCostumerUI;
import ui.showrequest.ListFiguresByCostumerUI;
import utils.AuthUtils;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for registering a new show request.
 * This controller manages user interaction to collect the necessary information
 * and register a show request, including customer, figures, description, location,
 * date, number of drones, and show duration.
 */
public class RegisterShowRequestController {

    // User interface components for fetching data
    private final FoundCostumerUI foundCostumerUI;
    private final ListFiguresByCostumerUI listFiguresByCostumerUI;

    // Data collected from the user for the show request
    private Costumer costumerSelected;
    private List<Figure> figuresSelected;
    private Description descriptionOfShowRequest;
    private Location locationOfShow;
    private LocalDateTime showDate;
    private int numberOfDrones;
    private Duration showDuration;

    /**
     * Constructor of the controller, initializing user interface components.
     */
    public RegisterShowRequestController() {
        this.foundCostumerUI = new FoundCostumerUI();
        this.listFiguresByCostumerUI = new ListFiguresByCostumerUI();

        // Initialize variables with default values
        this.costumerSelected = null;
        this.figuresSelected = null;
    }

    /**
     * Registers a new show request.
     * This method constructs and saves a show request based on the provided data.
     *
     * @return The registered show request.
     * @throws IOException If an error occurs during the registration process.
     */
    public ShowRequest registerShowRequest() throws IOException {
        // Attempts to automatically build a show request using the provided data
        Optional<ShowRequest> result = FactoryProvider.getShowRequestFactory().automaticBuild(costumerSelected, figuresSelected, descriptionOfShowRequest,
                locationOfShow, showDate, numberOfDrones, showDuration);

        // If show request construction is successful, attempts to save it to the repository
        if (result.isPresent()) {
            result = RepositoryProvider.showRequestRepository().saveInStore(result.get());
            if (result.isEmpty()){
                Utils.exitImmediately("❌ Failed to save the show request.");
            }
        } else {
            Utils.exitImmediately("❌ Failed to register the show request. Please check the input data and try again.");
        }

        // Logs the creation of the show request
        HistoryLogger<ShowRequest, Long> loggerEditer = new HistoryLogger<>();
        loggerEditer.logCreation(result.get(), AuthUtils.getCurrentUserEmail());
        return result.get();
    }

    /**
     * Finds and selects a customer for the show request.
     * If no customer is selected, an exception is thrown.
     */
    public void foundCostumerForRegistration(Optional<Costumer> result){
        if(result.isEmpty()){
            throw new IllegalArgumentException("No customer selected.");
        }
        costumerSelected = result.get();
    }

    /**
     * Finds and selects the figures associated with the selected customer.
     * If no figures are selected, an exception is thrown.
     */
    public void foundFiguresForRegistration(List<Figure> figures){
         if(figures.isEmpty()){
            throw new IllegalArgumentException("No figures selected.");
        }
        figuresSelected = figures;
    }

    /**
     * Sets the description for the show request.
     *
     * @param rawDescriptionOfShowRequest The raw description provided by the user.
     */
    public void getDescriptionsForRegistration(String rawDescriptionOfShowRequest){
        descriptionOfShowRequest = new Description(rawDescriptionOfShowRequest);
    }

    /**
     * Creates a location object for the show.
     */
    public void getLocationOfShow(Location locationOfShow){
        this.locationOfShow = locationOfShow;
    }

    /**
     * Sets the show date.
     * The expected input format is: yyyy-MM-dd HH:mm
     */
    public void getDateForShow(LocalDateTime dateOfShow){
        this.showDate = dateOfShow;
    }

    /**
     * Sets the number of drones to be used in the show.
     */
    public void getNumberOfDrones(int numberOfDrones){
        this.numberOfDrones = numberOfDrones;
    }

    /**
     * Sets the show duration in minutes.
     */
    public void getShowDuration(int duration){
        this.showDuration = Duration.ofMinutes(duration);
    }
}
