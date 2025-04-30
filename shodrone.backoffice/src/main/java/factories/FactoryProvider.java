package factories;


import persistence.inmemory.InMemoryAuthenticationRepository;
import persistence.inmemory.InMemoryCustomerRepository;
import persistence.inmemory.InMemoryFigureCategoryRepository;
import persistence.inmemory.InMemoryShowRequestRepository;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.FigureCategoryRepository;
import persistence.interfaces.ShowRequestRepository;
import persistence.interfaces.AuthenticationRepository;
import persistence.jpa.JPAImpl.AuthenticationRepositoryJPAImpl;
import persistence.jpa.JPAImpl.ShowRequestJPAImpl;

public class FactoryProvider {
    private static FigureCategoryRepository figureCategoryRepository;
    private static CostumerRepository costumerRepository;
    private static ShowRequestRepository showRequestRepository;
    private static AuthenticationRepository authenticationRepository;

    private static final boolean USE_IN_MEMORY = true;

    public static FigureCategoryRepository figureCategoryRepository() {
        if (figureCategoryRepository == null) {
            if (USE_IN_MEMORY) {
                figureCategoryRepository = new InMemoryFigureCategoryRepository();
            } else {
                // figureCategoryRepository = new JpaFigureCategoryRepository();
            }
        }
        return figureCategoryRepository;
    }

    public static CostumerRepository costumerRepository() {
        if (costumerRepository == null) {
            if (USE_IN_MEMORY) {
                costumerRepository = new InMemoryCustomerRepository();
            } else {
                // costumerRepository = new JpaCustomerRepository();
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