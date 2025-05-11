package controller.category;

import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

/**
 * Controller responsible for editing an existing figure category.
 */
public class EditFigureCategoryController {

    private final FigureCategoryRepository repository;

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public EditFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    /**
     * Edits the selected figure category, updating its name and/or description.
     *
     * @param category      the category to edit
     * @param newName       the new name, or null to keep current
     * @param newDescription the new description, or null to keep current
     * @return an Optional containing the updated FigureCategory, or empty if the operation failed
     */
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        return repository.editChosenCategory(category, newName, newDescription);
    }
}
