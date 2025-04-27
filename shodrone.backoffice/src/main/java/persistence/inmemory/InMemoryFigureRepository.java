package persistence.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainAutoNumberRepository;
import figuremanagement.domain.Figure;
import figuremanagement.repositories.FigureRepository;

public class InMemoryFigureRepository extends InMemoryDomainAutoNumberRepository<Figure>
        implements FigureRepository {
}
