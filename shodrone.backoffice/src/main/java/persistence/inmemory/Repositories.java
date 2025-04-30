package persistence.inmemory;

import persistence.interfaces.CostumerRepository;
import persistence.interfaces.ShowRequestRepository;
import persistence.jpa.JPAImpl.AuthenticationRepositoryJPAImpl;
import persistence.interfaces.AuthenticationRepository;


/**
 * Singleton class that manages multiple repositories used in the application.
 * This class provides access to different repository instances such as
 * AuthenticationRepository, MachineRepository, ItemRepository, IDRepository,
 * and OperationRepository.
 */
public class Repositories {

    private static Repositories instance;
    private final InMemoryAuthenticationRepository inMemoryAuthenticationRepository;
    private final AuthenticationRepository jpaAuthenticationRepository;
    private final InMemoryShowRequestRepository inMemoryShowRequestRepository;
    private final InMemoryCustomerRepository inMemoryCustomerRepository;
    //private final InMemoryFigureCategoryRepository inMemoryFigureCategoryRepository;

    /**
     * Private constructor for Repositories.
     * Initializes the various repositories used in the application.
     */
    private Repositories() {
        inMemoryAuthenticationRepository = new InMemoryAuthenticationRepository();
        jpaAuthenticationRepository = new AuthenticationRepositoryJPAImpl();
        inMemoryShowRequestRepository = new InMemoryShowRequestRepository();
        inMemoryCustomerRepository = new InMemoryCustomerRepository();
        //inMemoryFigureCategoryRepository = new InMemoryFigureCategoryRepository();
    }

    /**
     * Provides access to the singleton instance of Repositories.
     *
     * @return the single instance of Repositories
     */
    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    /**
     * Gets the AuthenticationRepository instance.
     *
     * @return the AuthenticationRepository instance
     */
    public InMemoryAuthenticationRepository getAuthenticationRepository() {
        return inMemoryAuthenticationRepository;
    }

    /**
     * Gets the AuthenticationRepository JPA implementation instance.
     *
     * @return the AuthenticationRepository (JPA) instance
     */
    public AuthenticationRepository getJpaAuthenticationRepository() {
        return jpaAuthenticationRepository;
    }

    public ShowRequestRepository getShowRequestRepository() {
        return inMemoryShowRequestRepository;
    }

    public CostumerRepository getCostumerRepository() {
        return inMemoryCustomerRepository;
    }

//    public FigureCategoryRepository getFigureCategoryRepository() {
//        return inMemoryFigureCategoryRepository;
//    }


}