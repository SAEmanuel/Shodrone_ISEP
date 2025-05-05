package controller;

import domain.entity.ShowRequest;
import domain.history.HistoryLogger;
import persistence.RepositoryProvider;
import utils.AuthUtils;
import utils.Utils;
import java.time.LocalDateTime;
import java.util.Optional;

public class EditShowRequestController {
    private final HistoryLogger<ShowRequest,Long> loggerEditer;
    private final String MODIFICATION_AUTHOR = AuthUtils.getCurrentUserEmail();
    private final LocalDateTime MODIFICATION_DATE = LocalDateTime.now();

    public EditShowRequestController() {
        loggerEditer = new HistoryLogger<>();
    }

    public Optional<ShowRequest> editShowRequest(ShowRequest oldRequest, ShowRequest newRequest){
        Optional<ShowRequest> result = Optional.empty();

        try {
            newRequest.setModificationAuthor(MODIFICATION_AUTHOR);
            newRequest.setModificationDate(MODIFICATION_DATE);
            result = RepositoryProvider.showRequestRepository().updateShowRequest(newRequest);

            if(result.isEmpty()){
                throw new IllegalArgumentException("Failed to edit ShowRequest.");
            }

            String author = AuthUtils.getCurrentUserEmail();
            loggerEditer.logChange(oldRequest, newRequest, author);

            result = Optional.of(newRequest);

        } catch (Exception e) {
            Utils.printFailMessage("Failed to edit ShowRequest."+e.getMessage());
        }

        return result;
    }



}
