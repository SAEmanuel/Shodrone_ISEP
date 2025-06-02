package ui.showproposal;

import controller.showproposal.CreateShowProposalController;
import controller.showproposal.GetAllShowRequestsController;
import controller.showrequest.ListFiguresByCostumerController;
import domain.entity.Figure;
import domain.entity.ShowProposal;
import domain.entity.ShowRequest;
import domain.entity.ShowTemplate;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.Video;
import factories.FactoryProvider;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static java.time.Duration.ofMinutes;

public class CreateShowProposalUI implements Runnable{
    private final CreateShowProposalController controller;
    //private final ShowProposalTemplateController templateController;
    private final GetAllShowRequestsController getAllShowRequestsController;
    private final ListFiguresByCostumerController listFiguresByCostumerController;
    private static final int EXIT = -1;

    public CreateShowProposalUI(){
        controller = new CreateShowProposalController();
        getAllShowRequestsController = new GetAllShowRequestsController();
        listFiguresByCostumerController = new ListFiguresByCostumerController();
    }

    @Override
    public void run(){
        Utils.printCenteredTitle("CREATE SHOW PROPOSAL");

        try{
            boolean option = false;

            Optional<List<ShowRequest>> listOfShowRequests = getAllShowRequestsController.listShowRequest();
            Optional<ShowRequest> showRequest = Optional.empty();
            if (listOfShowRequests.isPresent() && !listOfShowRequests.get().isEmpty()) {
                int index = Utils.showAndSelectIndexPartially(listOfShowRequests.get(), "Show Request");

                if (index == EXIT) {
                    Utils.printFailMessage("No show request selected...");
                    return;
                }

                showRequest = Optional.ofNullable(listOfShowRequests.get().get(index));
            }else{
                Utils.printFailMessage("❌ The are no show request to add to show proposal! Added them first and try again!");
                return;
            }

            Optional<ShowTemplate> template = Optional.empty();

            /*Optional<List<Template>> listOfTemplates = templateController.getTemplateList();
            Optional<Template> template = Optional.empty();
            if (listOfTemplates.isPresent() && !listOfTemplates.get().isEmpty()) {
                int index = Utils.showAndSelectIndexPartially(listOfTemplates.get(), "Templates");

                if (index == EXIT) {
                    Utils.printFailMessage("No template selected...");
                    return;
                }

                template = Optional.ofNullable(listOfTemplates.get().get(index));

            }else{
                Utils.printFailMessage("The are no templates to add to show proposal! Added them first and try again!");
                return;
            }*/

            Optional<List<Figure>> listOfFigures = listFiguresByCostumerController.listFiguresByCostumer(showRequest.get().getCostumer());
            List<Figure> sequenceFigures = new ArrayList<>();

            boolean selecting = true;
            if (listOfFigures.isPresent() && !listOfFigures.get().isEmpty()) {
                while(selecting) {
                    int index = Utils.showAndSelectIndexPartially(listOfFigures.get(), "Figures");

                    if (index == EXIT) {
                        Utils.printFailMessage("No figure selected...");
                        return;
                    }

                    Figure figureToAdd = listOfFigures.get().get(index);

                    if (figureToAdd != null && figureToAdd.identity() != null) {
                        sequenceFigures.add(figureToAdd);
                        listOfFigures.get().remove(figureToAdd);
                    }

                    selecting = Utils.confirm("Do you want to add more?");
                }
            }else{
                Utils.printFailMessage("❌ The are no figures to add to show proposal! Added them first and try again!");
                return;
            }

            Utils.dropLines(3);
            Utils.showDescriptionRules();
            option = Utils.confirm("Do you want to add a Description? (y/n)");
            Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
            Description description = descriptionOpt.orElse(null);

            Utils.dropLines(3);
            Utils.showNameRules();
            Location location = FactoryProvider.getLocationFactoryImpl().createLocationObject();

            Utils.dropLines(3);
            //Utils.showDateRules();// (yyyy-MM-dd HH:mm)
            LocalDateTime showDate = Utils.readDateFromConsole("Enter the show date: ");

            Utils.dropLines(3);
            int numberOfDrones = Utils.readIntegerFromConsole("Enter the number of Drones: ");

            Utils.dropLines(3);
            //Utils.showDurationRules();
            Duration showDuration = ofMinutes(Utils.readIntegerFromConsolePositive("Enter the show duration (minutes)"));

            Optional<ShowProposal> registeredShowProposal = controller.registerShowProposal(
                    showRequest.get(),
                    template.orElse(null),
                    sequenceFigures,
                    description,
                    location,
                    showDate,
                    numberOfDrones,
                    showDuration,
                    "Prop5"
            );

            Utils.dropLines(10);
            Utils.printShowProposalResume(registeredShowProposal.get());
            Utils.printSuccessMessage("\n✅ Show proposal successfully registered!");
            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printFailMessage(e.getMessage());
        }
    }

    /**
     * Helper method to optionally accept or skip input for a value object.
     * If user chooses to skip, returns empty Optional.
     * Otherwise, prompts repeatedly until valid input is entered.
     *
     * @param option boolean indicating whether the user wants to provide the value
     * @param prompt label of the input requested
     * @param parser function that converts String input into the target value object
     * @param clazz class of the target value object (not used here but kept for signature consistency)
     * @param <T> type of value object
     * @return Optional containing the parsed value or empty if skipped
     */
    private <T> Optional<T> refurseOrAcceptValueObject(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptWhileInvalid("Enter the "+ prompt, parser);
            return Optional.of(value);
        }
    }
}
