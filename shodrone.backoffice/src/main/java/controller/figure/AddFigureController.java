package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.FigureRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller responsible for handling the logic to add new Figure entities.
 */
public class AddFigureController {
    // Repository to handle persistence operations for Figure entities
    private final FigureRepository repository;

    /**
     * Constructor initializes the figure repository using a repository provider.
     */
    public AddFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    /**
     * Adds a new Figure to the repository.
     *
     * @param name         The name of the figure
     * @param description  A textual description of the figure
     * @param category     The category the figure belongs to
     * @param availability The availability status of the figure
     * @param status       The current status of the figure
     * @param costumer     The associated costumer (client/user)
     * @return An Optional containing the saved Figure if successful, or empty if the save failed
     */
    public Optional<Figure> addFigure(Name name, Description description, FigureCategory category, FigureAvailability availability, FigureStatus status, Map<String, List<String>> dslVersions, Costumer costumer) {
        Figure figure = new Figure(name, description, category, availability, status, dslVersions, costumer);
        return repository.save(figure);
    }
}
