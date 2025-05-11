package controller.figure;

import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.List;
import java.util.Optional;

public class ListPublicFiguresController {
    private final FigureRepository repository;

    public ListPublicFiguresController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<List<Figure>> listPublicFigures() {
        List<Figure> allPublicFigures = repository.findAllPublicFigures();
        return allPublicFigures.isEmpty() ? Optional.empty() : Optional.of(allPublicFigures);
    }
}
