package controller.showrequest;

import domain.entity.ShowRequest;
import history.HistoryLogger;
import persistence.RepositoryProvider;
import utils.AuthUtils;
import utils.Utils;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Controller responsible for handling the editing of a ShowRequest.
 * This includes logging the changes and updating the ShowRequest in the repository.
 */
public class EditShowRequestController {

    // Logger for tracking changes to the ShowRequest
    private final HistoryLogger<ShowRequest, Long> loggerEditer;

    // The author of the modification (current user)
    private final String MODIFICATION_AUTHOR = AuthUtils.getCurrentUserEmail();

    // The date and time of the modification
    private final LocalDateTime MODIFICATION_DATE = LocalDateTime.now();

    /**
     * Constructor of the controller, initializing the logger for tracking changes.
     */
    public EditShowRequestController() {
        loggerEditer = new HistoryLogger<>();
    }

    /**
     * Edits a ShowRequest by updating it in the repository and logging the changes.
     *
     * @param oldRequest The original ShowRequest that is being edited.
     * @param newRequest The updated ShowRequest with new data.
     * @return An Optional containing the updated ShowRequest if successful, or empty if the update failed.
     */
    public Optional<ShowRequest> editShowRequest(ShowRequest oldRequest, ShowRequest newRequest) {
        Optional<ShowRequest> result = Optional.empty();

        try {
            // Set the author and modification date for the new request
            newRequest.setModificationAuthor(MODIFICATION_AUTHOR);
            newRequest.setModificationDate(MODIFICATION_DATE);

            // Attempt to update the ShowRequest in the repository
            result = RepositoryProvider.showRequestRepository().updateShowRequest(newRequest);

            // Check if the update was successful
            if (result.isEmpty()) {
                throw new IllegalArgumentException("Failed to edit ShowRequest.");
            }

            // Log the change in the history logger
            String author = AuthUtils.getCurrentUserEmail();
            loggerEditer.logChange(oldRequest, newRequest, author);

            // Return the updated ShowRequest
            result = Optional.of(newRequest);

        } catch (Exception e) {
            // Print a failure message if an exception occurs
            Utils.printFailMessage("Failed to edit ShowRequest. " + e.getMessage());
        }

        return result;
    }
}
