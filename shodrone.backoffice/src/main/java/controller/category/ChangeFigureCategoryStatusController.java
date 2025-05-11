package controller.category;

import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

/**
 * Controller responsible for changing the status (active/inactive) of a figure category.
 */
public class ChangeFigureCategoryStatusController {
    private final FigureCategoryRepository repository;

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public ChangeFigureCategoryStatusController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    /**
     * Changes the status (active/inactive) of the selected figure category.
     *
     * @param selectedCategory the category to change status
     * @return an Optional containing the updated FigureCategory, or empty if the operation failed
     */
    public Optional<FigureCategory> changeStatus(FigureCategory selectedCategory) {
        return repository.changeStatus(selectedCategory);
    }
}
