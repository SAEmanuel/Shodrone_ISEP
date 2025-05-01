package persistence.interfaces;

import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;

import java.util.List;
import java.util.Optional;

public interface FigureCategoryRepository {

    Optional<FigureCategory> save(FigureCategory category);

    Optional<FigureCategory> findByName(String name);

    List<FigureCategory> findAll();

    Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription);


}
