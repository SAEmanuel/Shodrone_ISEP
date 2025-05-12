package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.FigureCategoryRepository;
import persistence.interfaces.FigureRepository;
import domain.history.AuditLoggerService;

import java.util.*;

public class InMemoryFigureRepository implements FigureRepository {
    private final Map<Long, Figure> store = new HashMap<>();
    private final AuditLoggerService auditLoggerService;
    private static long LAST_FIGURE_ID = 1L;

    public InMemoryFigureRepository(AuditLoggerService auditLoggerService) {
        super();
        this.auditLoggerService = auditLoggerService;
    }

    @Override
    public Optional<Figure> save(Figure figure) {
        if (figure == null) return Optional.empty();

        if (figure.identity() == null) {
            figure.setFigureId(LAST_FIGURE_ID++);
        }

        // Persist or reuse figure category
        Optional<FigureCategory> category = Optional.ofNullable(figure.category());
        FigureCategoryRepository categoryRepository = RepositoryProvider.figureCategoryRepository();

        if (category.isPresent() && category.get().identity() != null) {
            Optional<FigureCategory> existingCategory = categoryRepository.findByName(category.get().identity());

            if (existingCategory.isEmpty()) {
                category = categoryRepository.save(category.get());
            } else {
                category = Optional.of(existingCategory.get());
            }
            figure.UpdateFigureCategory(category.get()); 
        }

        
        
        // Persist or reuse costumer
        Optional<Costumer> costumer = Optional.ofNullable(figure.costumer());
        CostumerRepository costumerRepository = RepositoryProvider.costumerRepository();

        if (costumer.isPresent() && costumer.get().nif() != null) {
            Optional<Costumer> existingCostumer = costumerRepository.findByNIF(costumer.get().nif());

            if (existingCostumer.isEmpty()) {
                costumer = costumerRepository.saveInStore(costumer.get(), costumer.get().nif());
            } else {
                costumer = Optional.of(existingCostumer.get());
            }
            figure.UpdateFigureCostumer(costumer.get());
        }


        Optional<List<Figure>> findExistentFigure = findFigures(null, figure.name, null, null, figure.category(),
                null, null, null, figure.costumer());
        if( findExistentFigure.isPresent() && !findExistentFigure.get().isEmpty()) {
            return Optional.empty();
        }

        store.put(figure.identity(), figure);
        return Optional.of(figure);
        
    }

    @Override
    public List<Figure> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Figure> findAllActive() {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {
            if (figure.status().equals(FigureStatus.ACTIVE)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {
            if (figure.costumer().equals(costumer)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    @Override
    public Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        ArrayList<Figure> figures = new ArrayList();
        if(store.values().isEmpty())
            return Optional.ofNullable(figures);

        if (figureId != null) {
            Figure figure = store.get(figureId);
            if (figure != null) {
                return Optional.of(List.of(figure));
            }
        }


        int searching = 1;
        ArrayList<Figure> searchFigures = new ArrayList<>(store.values());

        while(searching <= 8) {
            figures = new ArrayList<>();
            for (Figure figure : searchFigures) {

                switch (searching) {
                    case 1:
                        if(name != null) {
                            if(figure.name().equals(name))
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 2:
                        if(description != null) {
                            if(figure.description().equals(description))
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 3:
                        if(version != null) {
                            if (figure.version().equals(version))
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 4:
                        if(category != null) {
                            if(figure.category().equals(category))
                                figures.add(figure);
                        }else {
                            figures.add(figure);
                        }
                        break;
                    case 5:
                        if(availability != null){
                            if( figure.availability().toString().equals(availability.toString()) )
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 6:
                        if(status != null){
                            if( figure.status().toString().equals(status.toString()) )
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 7:
                        if(dsl != null){
                            if( figure.dsl.toString().equals(dsl.toString()) )
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                    case 8:
                        if(costumer != null){
                            if(figure.costumer().equals(costumer))
                                figures.add(figure);
                        }else{
                            figures.add(figure);
                        }
                        break;
                }
            }
            searchFigures = figures;
            searching++;
        }

        return Optional.of(figures);
    }


    @Override
    public List<Figure> findAllPublicFigures() {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {

            if (figure.status().equals(FigureStatus.ACTIVE) && figure.availability().equals(FigureAvailability.PUBLIC)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    @Override
    public Optional<Figure> editChosenFigure(Figure figure) {
        Figure myFigure = null;
        if (store.containsKey(figure.identity())) {
            myFigure = store.get(figure.identity());
            myFigure.decommissionFigureStatus();
            if ( myFigure.status().equals(FigureStatus.INACTIVE) )
                return Optional.of(myFigure);
        }

        return Optional.empty();
    }
}
