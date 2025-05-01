package controller;

import authz.Email;
import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import more.Description;
import more.Name;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

public class AddFigureCategoryController {
    private final FigureCategoryRepository repository;

    public AddFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> addFigureCategoryWithNameAndDescription(Name name, Description description, Email createdBy) {
        FigureCategory category = new FigureCategory(name, description, createdBy);
        return repository.save(category);
    }

    public Optional<FigureCategory> addFigureCategoryWithName(Name name, Email createdBy) {
        FigureCategory category = new FigureCategory(name, createdBy);
        return repository.save(category);
    }
}
