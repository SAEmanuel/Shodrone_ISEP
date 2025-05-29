package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.FigureRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for searching Figure entities based on various filter criteria.
 */
public class SearchFigureController {
    // Repository to handle persistence operations for Figure entities
    private final FigureRepository repository;

    /**
     * Constructor initializes the figure repository using a repository provider.
     */
    public SearchFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    /**
     * Searches for figures using the provided filter parameters. Any parameter can be null to indicate it should not be used as a filter.
     *
     * @param figureId      The unique identifier of the figure (optional)
     * @param name          The name of the figure (optional)
     * @param description   The description of the figure (optional)
     * @param version       The version of the figure (optional)
     * @param category      The category the figure belongs to (optional)
     * @param availability  The availability status of the figure (optional)
     * @param status        The current status of the figure (optional)
     * @param dsl           The domain-specific language details of the figure (optional)
     * @param costumer      The costumer associated with the figure (optional)
     * @return An Optional containing a list of figures that match the search criteria, or an empty Optional if no matches are found
     */
    public Optional<List<Figure>> searchFigure (Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        return repository.findFigures(figureId, name, description, version, category, availability, status, dsl, costumer);
    }
}
