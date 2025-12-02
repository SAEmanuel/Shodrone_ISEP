package controller.category;

import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import persistence.FigureCategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for retrieving figure categories from the repository.
 */
public class GetFigureCategoriesController {

    private final FigureCategoryRepository repository;

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public GetFigureCategoriesController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    /**
     * Retrieves all figure categories.
     *
     * @return an Optional containing the list of all categories, or empty if none exist
     */
    public Optional<List<FigureCategory>> getAllFigureCategories() {
        List<FigureCategory> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }

    /**
     * Retrieves all active figure categories.
     *
     * @return an Optional containing the list of active categories, or empty if none exist
     */
    public Optional<List<FigureCategory>> getActiveFigureCategories() {
        List<FigureCategory> all = repository.findActiveCategories();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
