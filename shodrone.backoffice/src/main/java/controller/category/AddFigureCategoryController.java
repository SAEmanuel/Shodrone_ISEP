package controller.category;

import authz.Email;
import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

/**
 * Controller responsible for adding new figure categories.
 * Provides methods to create categories with or without a description.
 */
public class AddFigureCategoryController {
    private final FigureCategoryRepository repository;

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public AddFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    /**
     * Adds a new figure category with a name and description.
     *
     * @param name        the category name
     * @param description the category description
     * @param createdBy   the user creating the category
     * @return an Optional containing the created FigureCategory, or empty if the name is not unique
     */
    public Optional<FigureCategory> addFigureCategoryWithNameAndDescription(Name name, Description description, Email createdBy) {
        FigureCategory category = new FigureCategory(name, description, createdBy);
        return repository.save(category);
    }

    /**
     * Adds a new figure category with only a name.
     *
     * @param name      the category name
     * @param createdBy the user creating the category
     * @return an Optional containing the created FigureCategory, or empty if the name is not unique
     */
    public Optional<FigureCategory> addFigureCategoryWithName(Name name, Email createdBy) {
        FigureCategory category = new FigureCategory(name, createdBy);
        return repository.save(category);
    }
}
