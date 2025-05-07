package controller;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.List;
import java.util.Optional;

public class SearchFigureController {
    private final FigureRepository repository;

    public SearchFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<List<Figure>> searchFigure (Long figureId, String name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, Costumer costumer) {
        return repository.findFigures(figureId, name, description, version, category, availability, status, costumer);
    }
}
