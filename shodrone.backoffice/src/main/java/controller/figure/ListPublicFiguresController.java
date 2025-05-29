package controller.figure;

import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.FigureRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for retrieving all publicly visible Figure entities.
 */
public class ListPublicFiguresController {
    // Repository to handle persistence operations for Figure entities
    private final FigureRepository repository;

    /**
     * Constructor initializes the figure repository using a repository provider.
     */
    public ListPublicFiguresController() {
        repository = RepositoryProvider.figureRepository();
    }

    /**
     * Retrieves all figures marked as public from the repository.
     *
     * @return An Optional containing a list of public Figure objects if any exist,
     *         or an empty Optional if the list is empty
     */
    public Optional<List<Figure>> listPublicFigures() {
        List<Figure> allPublicFigures = repository.findAllPublicFigures();
        return allPublicFigures.isEmpty() ? Optional.empty() : Optional.of(allPublicFigures);
    }
}
