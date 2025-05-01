package controller;

import domain.entity.FigureCategory;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;

import java.util.Optional;

public class ChangeFigureCategoryStatusController {
    private final FigureCategoryRepository repository;

    public ChangeFigureCategoryStatusController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

   public Optional<FigureCategory> changeStatus(FigureCategory selectedCategory) {
        return repository.changeStatus(selectedCategory);
   }

}
