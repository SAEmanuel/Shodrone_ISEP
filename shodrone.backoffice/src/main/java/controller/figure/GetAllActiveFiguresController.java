package controller.figure;

import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.FigureRepository;

import java.util.List;
import java.util.Optional;


/**
 * Controller responsible for retrieving all active Figure entities from the repository.
 */
public class GetAllActiveFiguresController {
    // Repository to handle persistence operations for Figure entities
    private final FigureRepository repository;

    /**
     * Constructor initializes the figure repository using a repository provider.
     */
    public GetAllActiveFiguresController() {
        repository = RepositoryProvider.figureRepository();
    }

    /**
     * Retrieves all active figures from the repository.
     *
     * @return An Optional containing a list of active Figure objects if any are found, or an empty Optional if none are found
     */
    public Optional<List<Figure>> getAllActiveFigures () {
        return Optional.ofNullable(repository.findAllActive());
    }
}