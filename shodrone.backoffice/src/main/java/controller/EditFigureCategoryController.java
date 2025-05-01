package controller;

import domain.entity.FigureCategory;
import more.Description;
import more.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

public class EditFigureCategoryController {

    private final FigureCategoryRepository repository;

    public EditFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        return repository.editChosenCategory(category, newName, newDescription);
    }

}
