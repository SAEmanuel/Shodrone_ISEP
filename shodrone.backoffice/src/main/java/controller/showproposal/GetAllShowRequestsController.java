package controller.showproposal;

import domain.entity.ShowRequest;
import persistence.RepositoryProvider;

import java.util.List;
import java.util.Optional;

public class GetAllShowRequestsController {

    public GetAllShowRequestsController() {}

    public Optional<List<ShowRequest>> listShowRequest() {
        // Fetch the show requests from the repository
        Optional<List<ShowRequest>> showRequestList = Optional.ofNullable(RepositoryProvider.showRequestRepository().getAll());

        // If show requests are found, return them; otherwise, throw an exception
        if (showRequestList.isPresent()) {
            return showRequestList;
        } else {
            throw new IllegalArgumentException("No show requests found.");
        }
    }
}
