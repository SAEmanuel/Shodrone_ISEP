package controller.figure;

import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

/**
 * Controller responsible for handling the decommissioning (or updating) of existing Figure entities.
 */
public class DecommissionFigureController {
    // Repository to handle persistence operations for Figure entities
    private final FigureRepository repository;

    /**
     * Constructor initializes the figure repository using a repository provider.
     */
    public DecommissionFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    /**
     * Updates the given figure in the repository, typically used for decommissioning or modifying its state.
     *
     * @param figure The Figure object to be edited or decommissioned
     * @return An Optional containing the updated Figure if successful, or empty if the update failed
     */
    public Optional<Figure> editChosenFigure(Figure figure) {
        return repository.editChosenFigure(figure);
    }
}