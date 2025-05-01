package controller;

import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.List;
import java.util.Optional;

public class ListAllFigureCategoriesController {

    private final FigureCategoryRepository repository;

    public ListAllFigureCategoriesController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<List<FigureCategory>> getAllFigureCategories() {
        List<FigureCategory> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
