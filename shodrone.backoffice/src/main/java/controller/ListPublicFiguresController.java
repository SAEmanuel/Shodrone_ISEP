package controller;

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

    public List<Figure> listPublicFigures() {
        return repository.findAllPublicFigures();
    }
}
