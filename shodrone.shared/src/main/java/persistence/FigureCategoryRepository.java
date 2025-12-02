package persistence;

import domain.entity.FigureCategory;

import java.util.List;
import java.util.Optional;

public interface FigureCategoryRepository {

    Optional<FigureCategory> save(FigureCategory category);

    Optional<FigureCategory> findByName(String name);

    List<FigureCategory> findAll();

    List<FigureCategory> findActiveCategories();

    Optional<FigureCategory> updateFigureCategory(FigureCategory category, String oldKey);



}
