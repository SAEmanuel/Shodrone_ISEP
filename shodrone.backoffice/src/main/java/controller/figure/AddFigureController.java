package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.FigureRepository;
import utils.DslMetadata;

import java.util.Map;
import java.util.Optional;

public class AddFigureController {
    private final FigureRepository repository;

    public AddFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<Figure> addFigure(Name name, Description description, FigureCategory category,
                                      FigureAvailability availability, FigureStatus status,
                                      Map<String, DslMetadata> dslVersions, Costumer costumer) {
        Figure figure = new Figure(name, description, category, availability, status, dslVersions, costumer);
        return repository.save(figure);
    }
}