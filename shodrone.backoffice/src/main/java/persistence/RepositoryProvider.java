package persistence;


import persistence.inmemory.*;
import persistence.interfaces.*;
import persistence.jpa.JPAImpl.*;

public class RepositoryProvider {
    private static FigureCategoryRepository figureCategoryRepository;
    private static FigureRepository figureRepository;
    private static CostumerRepository costumerRepository;
    private static ShowRequestRepository showRequestRepository;
    private static AuthenticationRepository authenticationRepository;

    private static final boolean USE_IN_MEMORY = true;

    public static FigureCategoryRepository figureCategoryRepository() {
        if (figureCategoryRepository == null) {
            if (USE_IN_MEMORY) {
                figureCategoryRepository = new InMemoryFigureCategoryRepository();
            } else {
                 figureCategoryRepository = new FigureCategoryJPAImpl();
            }
        }
        return figureCategoryRepository;
    }

    public static FigureRepository figureRepository() {
        if (figureRepository == null) {
            if (USE_IN_MEMORY) {
                figureRepository = new InMemoryFigureRepository();
            } else {
                figureRepository = new FigureRepositoryJPAImpl();
            }
        }
        return figureRepository;
    }

    public static CostumerRepository costumerRepository() {
        if (costumerRepository == null) {
            if (USE_IN_MEMORY) {
                costumerRepository = new InMemoryCustomerRepository();
            } else {
                costumerRepository = new CostumerJPAImpl();
            }
        }
        return costumerRepository;
    }

    public static ShowRequestRepository showRequestRepository() {
        if (showRequestRepository == null) {
            if (USE_IN_MEMORY) {
                showRequestRepository = new InMemoryShowRequestRepository();
            } else {
                showRequestRepository = new ShowRequestJPAImpl();
            }
        }
        return showRequestRepository;
    }

    public static AuthenticationRepository authenticationRepository() {
        if (authenticationRepository == null) {
            if (USE_IN_MEMORY) {
                authenticationRepository = new InMemoryAuthenticationRepository();
            } else {
                authenticationRepository = new AuthenticationRepositoryJPAImpl();
            }
        }
        return authenticationRepository;
    }
}