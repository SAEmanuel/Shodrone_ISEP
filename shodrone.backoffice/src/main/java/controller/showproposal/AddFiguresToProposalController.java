package controller.showproposal;

import domain.entity.Figure;
import domain.entity.ShowProposal;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for handling the logic of adding figures
 * to an existing {@link ShowProposal}.
 *
 * <p>This controller serves as an intermediary between the UI layer
 * and the repository layer. It allows the user to:
 * <ul>
 *     <li>Retrieve all proposals stored in the system</li>
 *     <li>Save an updated list of figures into a selected proposal</li>
 * </ul>
 *
 * <p>Typical use case: invoked from a UI component such as {@link ui.showproposal.AddFiguresToProposalUI}.
 *
 * <p>Relies on {@link persistence.ShowProposalRepository} for data persistence.
 *
 * @author Catarina
 */
public class AddFiguresToProposalController {

    /**
     * Constructs a new instance of the controller.
     */
    public AddFiguresToProposalController() {}

    /**
     * Retrieves all {@link ShowProposal} objects from the repository.
     *
     * @return a list of show proposals
     * @throws RuntimeException if no proposals are found
     */
    public List<ShowProposal> getAllProposals() {
        Optional<List<ShowProposal>> optionalResult = RepositoryProvider.showProposalRepository().getAllProposals();

        if (optionalResult.isEmpty()) {
            throw new RuntimeException("No Show Proposal's were found on the system.");
        }

        return optionalResult.get();
    }

    /**
     * Saves a new sequence of figures into a given {@link ShowProposal},
     * overwriting its existing figure sequence.
     *
     * @param showProposal the proposal to update
     * @param sequenceOfFigures the new sequence of figures to assign
     * @return an Optional containing the updated ShowProposal, if saved successfully
     */
    public Optional<ShowProposal> saveNewImagesInProposal(ShowProposal showProposal, List<Figure> sequenceOfFigures) {
        showProposal.setSequenceFigues(sequenceOfFigures);
        return RepositoryProvider.showProposalRepository().saveInStore(showProposal);
    }
}
