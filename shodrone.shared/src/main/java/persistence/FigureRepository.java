package persistence;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining the contract for Figure data persistence operations.
 */
public interface FigureRepository {

    /**
     * Saves a Figure to the repository.
     * If the figure already exists (based on its identity or uniqueness rules), it may be rejected.
     *
     * @param figure The figure to save.
     * @return An Optional containing the saved figure, or empty if saving failed.
     */
    Optional<Figure> save(Figure figure);

    /**
     * Retrieves all figures in the repository.
     *
     * @return A list of all figures.
     */
    List<Figure> findAll();

    /**
     * Retrieves all figures that are marked as ACTIVE.
     *
     * @return A list of active figures.
     */
    List<Figure> findAllActive();

    /**
     * Decommissions (inactivates) a specific figure.
     *
     * @param figure The figure to edit.
     * @return An Optional containing the updated figure if successful, or empty if the figure was not found.
     */
    Optional<Figure> editChosenFigure(Figure figure);

    /**
     * Retrieves all active figures belonging to the given costumer.
     * Also includes public figures, regardless of owner.
     *
     * @param costumer The costumer whose figures are to be retrieved.
     * @return A list of matching figures.
     */
    List<Figure> findByCostumer(Costumer costumer);

    /**
     * Searches for figures matching the provided filter parameters.
     * All parameters are optional; only non-null ones are used in the search.
     *
     * @param figureId      Optional figure ID.
     * @param name          Optional figure name.
     * @param description   Optional description.
     * @param version       Optional version.
     * @param category      Optional category.
     * @param availability  Optional availability (PUBLIC or PRIVATE).
     * @param status        Optional status (ACTIVE or INACTIVE).
     * @param dsl           Optional DSL definition.
     * @param costumer      Optional costumer.
     * @return An Optional containing a list of matching figures, or an empty list if none found.
     */
    Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer);

    /**
     * Retrieves all public figures that are also marked as ACTIVE.
     *
     * @return A list of active public figures.
     */
    List<Figure> findAllPublicFigures();

}
