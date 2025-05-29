package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import history.AuditLoggerService;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.CostumerRepository;
import persistence.FigureCategoryRepository;
import persistence.FigureRepository;

import java.util.*;


/**
 * In-memory implementation of the FigureRepository interface.
 * Used for temporary data storage, typically in testing or prototyping environments.
 */
public class InMemoryFigureRepository implements FigureRepository {
    private final Map<Long, Figure> store = new HashMap<>();
    private final AuditLoggerService auditLoggerService;
    private static long LAST_FIGURE_ID = 1L;

    public InMemoryFigureRepository(AuditLoggerService auditLoggerService) {
        super();
        this.auditLoggerService = auditLoggerService;
    }

    /**
     * Saves a new figure to the repository.
     * If the figure already exists, it may not be saved.
     *
     * @param figure The figure to be saved.
     * @return An Optional containing the saved figure, or empty if saving failed.
     */
    @Override
    public Optional<Figure> save(Figure figure) {
        if (figure == null) return Optional.empty();

        if (figure.identity() == null) {
            figure.setFigureId(LAST_FIGURE_ID++);
        }

        // Persist or reuse figure category
        Optional<FigureCategory> category = Optional.ofNullable(figure.category());
        FigureCategoryRepository categoryRepository = RepositoryProvider.figureCategoryRepository();

        if (category.isPresent() && category.get().identity() != null) {
            Optional<FigureCategory> existingCategory = categoryRepository.findByName(category.get().identity());

            if (existingCategory.isEmpty()) {
                category = categoryRepository.save(category.get());
            } else {
                category = Optional.of(existingCategory.get());
            }
            figure.UpdateFigureCategory(category.get()); 
        }else{
            return Optional.empty();
        }

        
        
        // Persist or reuse costumer
        Optional<Costumer> costumer = Optional.ofNullable(figure.costumer());
        CostumerRepository costumerRepository = RepositoryProvider.costumerRepository();

        if (costumer.isPresent() && costumer.get().nif() != null) {
            Optional<Costumer> existingCostumer = costumerRepository.findByNIF(costumer.get().nif());

            if (existingCostumer.isEmpty()) {
                costumer = costumerRepository.saveInStore(costumer.get(), costumer.get().nif());
            } else {
                costumer = Optional.of(existingCostumer.get());
            }
            figure.UpdateFigureCostumer(costumer.get());
        }


        Optional<List<Figure>> findExistentFigure = findFigures(null, figure.name, null, null, figure.category(),
                null, null, null, figure.costumer());
        if( findExistentFigure.isPresent() && !findExistentFigure.get().isEmpty()) {
            return Optional.empty();
        }

        store.put(figure.identity(), figure);
        return Optional.of(figure);
        
    }

    /**
     * Returns all stored figures.
     *
     * @return A list of all figures.
     */
    @Override
    public List<Figure> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
           * Returns only figures marked as ACTIVE.
            *
            * @return A list of active figures.
     */
    @Override
    public List<Figure> findAllActive() {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {
            if (figure.status().equals(FigureStatus.ACTIVE)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    /**
     * Returns all active figures associated with a specific costumer,
     * or any public figures regardless of costumer.
     *
     * @param costumer The costumer used for filtering.
     * @return A list of figures matching the costumer or marked as public.
     */
    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {
            if ((figure.costumer().equals(costumer) && figure.status().equals(FigureStatus.ACTIVE)) || (figure.status().equals(FigureStatus.ACTIVE) && figure.availability().equals(FigureAvailability.PUBLIC)) ) {
                figures.add(figure);
            }
        }

        return figures;
    }

    /**
     * Searches for figures using a combination of optional parameters.
     * Filters are applied sequentially by each field.
     *
     * @param figureId      Optional figure ID to match.
     * @param name          Optional name to match.
     * @param description   Optional description to match.
     * @param version       Optional version to match.
     * @param category      Optional category to match.
     * @param availability  Optional availability to match.
     * @param status        Optional status to match.
     * @param dsl           Optional DSL to match.
     * @param costumer      Optional costumer to match.
     * @return An Optional list of matching figures, or an empty list if none found.
     */
    @Override
    public Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        ArrayList<Figure> figures = new ArrayList();
        if(store.values().isEmpty()) {
            return Optional.ofNullable(figures);
        }

        if (figureId != null) {
            if (store.containsKey(figureId)) {
                return Optional.of(Collections.singletonList(store.get(figureId)));
            }
        }

        int searching = 1;
        ArrayList<Figure> searchFigures = new ArrayList<>(store.values());

        while (searching <= 8) {
            figures = new ArrayList<>();

            for (Figure figure : searchFigures) {
                switch (searching) {
                    case 1:
                        if (name == null || figure.name().equals(name)) {
                            figures.add(figure);
                        }
                        break;
                    case 2:
                        if (description == null || figure.description().equals(description)) {
                            figures.add(figure);
                        }
                        break;
                    case 3:
                        if (version == null || figure.version().equals(version)) {
                            figures.add(figure);
                        }
                        break;
                    case 4:
                        if (category == null || figure.category().equals(category)) {
                            figures.add(figure);
                        }
                        break;
                    case 5:
                        if (availability == null || figure.availability().toString().equals(availability.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 6:
                        if (status == null || figure.status().toString().equals(status.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 7:
                        if (dsl == null || figure.dsl().toString().equals(dsl.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 8:
                        if (costumer == null || figure.costumer().equals(costumer)) {
                            figures.add(figure);
                        }
                        break;
                }
            }

            if (figures.isEmpty()) {
                break;
            }

            searchFigures = figures;
            searching++;
        }

        return Optional.of(figures);
    }

    /**
     * Returns all figures that are both active and public.
     *
     * @return A list of public, active figures.
     */
    @Override
    public List<Figure> findAllPublicFigures() {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {

            if (figure.status().equals(FigureStatus.ACTIVE) && figure.availability().equals(FigureAvailability.PUBLIC)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    /**
     * Edits a figure by decommissioning it (marking it as INACTIVE).
     * Only applies if the figure exists in the repository.
     *
     * @param figure The figure to be edited.
     * @return An Optional containing the updated figure, or empty if not found.
     */
    @Override
    public Optional<Figure> editChosenFigure(Figure figure) {
        Figure myFigure = null;
        if (store.containsKey(figure.identity())) {
            myFigure = store.get(figure.identity());
            myFigure.decommissionFigureStatus();
            if ( myFigure.status().equals(FigureStatus.INACTIVE) )
                return Optional.of(myFigure);
        }

        return Optional.empty();
    }
}
