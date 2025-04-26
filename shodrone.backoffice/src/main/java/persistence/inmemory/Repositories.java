package persistence.inmemory;

/**
 * Singleton class that manages multiple repositories used in the application.
 * This class provides access to different repository instances such as
 * AuthenticationRepository, MachineRepository, ItemRepository, IDRepository,
 * and OperationRepository.
 */
public class Repositories {

    private static Repositories instance;
    private final InMemoryAuthenticationRepository inMemoryAuthenticationRepository;


    /**
     * Private constructor for Repositories.
     * Initializes the various repositories used in the application.
     */
    private Repositories() {
        inMemoryAuthenticationRepository = new InMemoryAuthenticationRepository();
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
}