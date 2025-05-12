package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.List;
import java.util.Optional;

public class SearchFigureController {
    private final FigureRepository repository;

    public SearchFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<List<Figure>> searchFigure (Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        return repository.findFigures(figureId, name, description, version, category, availability, status, dsl, costumer);
    }
}
