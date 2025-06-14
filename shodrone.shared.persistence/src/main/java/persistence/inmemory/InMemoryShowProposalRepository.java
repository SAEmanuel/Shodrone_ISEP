package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.ShowProposal;
import domain.valueObjects.ShowProposalStatus;
import domain.valueObjects.Video;
import persistence.ShowProposalRepository;

import java.util.*;

/**
 * In-memory implementation of the {@link ShowProposalRepository} interface.
 *
 * <p>This class provides a mock implementation of the repository, storing {@link ShowProposal} instances
 * in a static in-memory data structure. It is intended primarily for testing and development scenarios
 * where persistent storage is not required.
 *
 * <p>The repository organizes proposals by their associated ShowRequest ID, enabling efficient lookups
 * and updates. It supports:
 * <ul>
 *     <li>Saving and updating proposals</li>
 *     <li>Retrieving all proposals</li>
 *     <li>Finding proposals by customer</li>
 *     <li>Getting videos associated with proposals</li>
 *     <li>Filtering proposals by status</li>
 * </ul>
 *
 * <p><b>Note:</b> This implementation is not thread-safe and not intended for production use.
 */
public class InMemoryShowProposalRepository implements ShowProposalRepository {

    /** In-memory store of proposals: maps ShowRequest ID → List of proposals */
    private static Map<Long, List<ShowProposal>> store = new HashMap<>();

    /** Counter to simulate auto-incrementing IDs */
    private static long LAST_SHOW_REQUEST_ID = 1L;

    /**
     * Saves a {@link ShowProposal} entity to memory.
     * If the entity has no ID, a new one is assigned.
     * If the proposal already exists, it is updated.
     *
     * @param entity the ShowProposal to save or update
     * @return the saved proposal, wrapped in Optional
     */
    @Override
    public Optional<ShowProposal> saveInStore(ShowProposal entity) {
        if (entity == null) {
            return Optional.empty();
        }

        if (entity.identity() == null) {
            entity.setShowProposalID(LAST_SHOW_REQUEST_ID++);
        }

        Long idShowRequest = entity.getShowRequest().identity();
        store.putIfAbsent(idShowRequest, new ArrayList<>());
        List<ShowProposal> proposals = store.get(idShowRequest);

        boolean updated = false;
        for (int i = 0; i < proposals.size(); i++) {
            ShowProposal existing = proposals.get(i);
            if (existing.identity().equals(entity.identity())) {
                proposals.set(i, entity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            proposals.add(entity);
        }

        return Optional.of(entity);
    }

    /**
     * Retrieves all {@link ShowProposal} objects from memory.
     *
     * @return an Optional containing all proposals, or empty if none exist
     */
    @Override
    public Optional<List<ShowProposal>> getAllProposals() {
        List<ShowProposal> listShowProposals = new ArrayList<>();
        for (List<ShowProposal> proposals : store.values()) {
            listShowProposals.addAll(proposals);
        }
        return listShowProposals.isEmpty() ? Optional.empty() : Optional.of(listShowProposals);
    }

    /**
     * Finds all proposals associated with a specific {@link Costumer}.
     *
     * @param costumer the customer whose proposals are sought
     * @return a list of proposals, or empty if none are found
     */
    @Override
    public Optional<List<ShowProposal>> findAllCostumerProposals(Costumer costumer) {
        if (store.containsKey(costumer.identity())) {
            return Optional.of(store.get(costumer.identity()));
        }
        return Optional.empty();
    }

    /**
     * Retrieves the {@link Video} associated with a given {@link ShowProposal}.
     *
     * @param proposal the proposal to inspect
     * @return an Optional containing the video, or empty if none is present
     */
    public Optional<Video> getVideoBytesByShowProposal(ShowProposal proposal) {
        List<ShowProposal> proposals = store.get(proposal.identity());
        if (proposals == null || proposals.isEmpty()) {
            return Optional.empty();
        }

        for (ShowProposal sp : proposals) {
            if (sp.identity().equals(proposal.identity())) {
                return Optional.ofNullable(sp.getVideo());
            }
        }

        return Optional.empty();
    }

    /**
     * Not yet implemented: finds a proposal by its ID.
     *
     * @param id the proposal's ID
     * @return Optional.empty()
     */
    @Override
    public Optional<ShowProposal> findByID(Long id) {
        return Optional.empty(); // TODO: Implement if needed
    }

    /**
     * Not yet implemented: finds a proposal by its name.
     *
     * @param nameProposal the name of the proposal
     * @return Optional.empty()
     */
    @Override
    public Optional<ShowProposal> findByName(String nameProposal) {
        return Optional.empty(); // TODO: Implement if needed
    }

    /**
     * Returns all proposals, serving as a fallback implementation for "updated" ones.
     *
     * @return a list of all proposals
     */
    @Override
    public Optional<List<ShowProposal>> getUpdatedProposals() {
        return getAllProposals(); // Placeholder for real filter
    }

    /**
     * Filters all proposals and returns those with {@link ShowProposalStatus#STAND_BY} status.
     *
     * @return list of standby proposals
     */
    @Override
    public Optional<List<ShowProposal>> getStandbyProposals() {
        Optional<List<ShowProposal>> allProposalsOpt = getAllProposals();

        if (allProposalsOpt.isEmpty()) {
            return Optional.empty();
        }

        List<ShowProposal> proposals = allProposalsOpt.get().stream()
                .filter(p -> p.getStatus() == ShowProposalStatus.STAND_BY)
                .toList();

        return proposals.isEmpty() ? Optional.empty() : Optional.of(proposals);
    }
}
