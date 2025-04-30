package controller;

import domain.entity.FigureCategory;
import factories.FactoryProvider;
import more.Description;
import more.Name;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

public class AddFigureCategoryController {
    private final FigureCategoryRepository repository;

    public AddFigureCategoryController() {
        repository = FactoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> addFigureCategoryWithNameAndDescription(Name name, Description description) {
        FigureCategory category = new FigureCategory(name, description);
        return repository.save(category);
    }

    public Optional<FigureCategory> addFigureCategoryWithName(Name name) {
        FigureCategory category = new FigureCategory(name);
        return repository.save(category);
    }
}
