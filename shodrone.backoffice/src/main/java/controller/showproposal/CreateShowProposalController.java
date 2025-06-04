package controller.showproposal;

import domain.entity.*;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import factories.FactoryProvider;
import history.HistoryLogger;
import persistence.RepositoryProvider;
import utils.AuthUtils;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CreateShowProposalController {

    // Data collected from the user for the show request
    private ShowRequest showRequest;
    private ProposalTemplate proposalTemplate;
    private List<Figure> sequenceFigures;
    private Description description;
    private Location location;
    private LocalDateTime showDate;
    private int numberOfDrones;
    private Duration showDuration;

    public CreateShowProposalController() {
    }

    public Optional<ShowProposal> registerShowProposal(
            ShowRequest showRequest,
            ProposalTemplate template,
            List<Figure> sequenceFigures,
            Description description,
            Location location,
            LocalDateTime showDate,
            int numberOfDrones,
            Duration showDuration,
            String version
    ) throws IOException {

        // Attempts to automatically build a show proposal using the provided data
        Optional<ShowProposal> result = FactoryProvider.getShowProposalFactory().automaticBuild(
                showRequest,
                sequenceFigures,
                description,
                location,
                showDate,
                numberOfDrones,
                showDuration,
                version
        );

        // If show request construction is successful, attempts to save it to the repository
        if (result.isPresent()) {
            System.out.println("result: " + result);
            System.out.println("%n%n");
            result = RepositoryProvider.showProposalRepository().saveInStore(result.get());
            if (result.isEmpty()) {
                Utils.exitImmediately("❌ Failed during save process of the show proposal.");
            }
        } else {
            Utils.exitImmediately("❌ Failed to register the show proposal. Please check the input data and try again.");
        }

        // Logs the creation of the show request
        HistoryLogger<ShowProposal, Long> loggerEditer = new HistoryLogger<>();
        loggerEditer.logCreation(result.get(), AuthUtils.getCurrentUserEmail());

        return result;
    }

    /**
     * Finds and selects the figures associated with the selected customer.
     * If no figures are selected, an exception is thrown.
     */
    public void foundFiguresForRegistration(List<Figure> figures){
        if(figures.isEmpty()){
            throw new IllegalArgumentException("No figures selected.");
        }
        sequenceFigures = figures;
    }

    /**
     * Sets the description for the show request.
     *
     * @param rawDescriptionOfShowRequest The raw description provided by the user.
     */
    public void getDescriptionsForRegistration(String rawDescriptionOfShowRequest){
        description = new Description(rawDescriptionOfShowRequest);
    }

    /**
     * Creates a location object for the show.
     */
    public void getLocationOfShow(Location locationOfShow){
        this.location = locationOfShow;
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
