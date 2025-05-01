package controller;

import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.List;


public class GetAllFigureCategoriesController {
    private final FigureCategoryRepository repository;

    public GetAllFigureCategoriesController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public List<FigureCategory> getAllFigureCategories() {
        return repository.findAll();
    }

}
