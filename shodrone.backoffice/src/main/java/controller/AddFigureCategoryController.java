package controller;

import domain.entity.FigureCategory;
import factories.FactoryProvider;
import more.Description;
import more.Name;
import persistence.inmemory.Repositories;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

public class AddFigureCategoryController {
    //todo after inMem implementation, implement JPA
    private final FigureCategoryRepository inMemRepository;

    public AddFigureCategoryController() {
        inMemRepository = FactoryProvider.figureCategoryRepository();
    }


    public Optional<FigureCategory> addFigureCategory(Name name, Description description) {
        FigureCategory category = new FigureCategory(name, description);
        return inMemRepository.save(category);
    }

    public void addFigureCategory(Name name) {
        if (inMemRepository.findByName(name.toString().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("Category name already exists.");
        }
        FigureCategory category = new FigureCategory(name);
        inMemRepository.save(category);
    }
}
