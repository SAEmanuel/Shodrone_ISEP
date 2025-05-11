package controller.showrequest;

import domain.entity.Costumer;
import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.List;
import java.util.Optional;

public class ListFiguresByCostumerController {
    private final FigureRepository figureRepository;

    public ListFiguresByCostumerController() {
        figureRepository = RepositoryProvider.figureRepository();
    }

    public Optional<List<Figure>> listFiguresByCostumer(Costumer costumer) {
        Optional<List<Figure>> listFigure = Optional.ofNullable(figureRepository.findByCostumer(costumer));
        if(listFigure.get().isEmpty()){
            throw new IllegalArgumentException("No figures found for the given costumer.");
        }
        return listFigure;
    }
}
