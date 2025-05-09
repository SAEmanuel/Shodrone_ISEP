package persistence;

import authz.UserRepository;
import domain.history.AuditLoggerService;
import lombok.Setter;
import persistence.inmemory.*;
import persistence.interfaces.*;
import persistence.jpa.JPAImpl.*;
/**
 * The {@code RepositoryProvider} class is responsible for providing access to persistence repositories
 * for various entities in the system. It uses the Singleton design pattern to ensure that only one instance
 * of each repository is created and maintained during the application's runtime.
 *
 * The implementation can be configured to use either in-memory repositories or JPA-based repositories,
 * depending on the need, through the {@link #setUseInMemory(boolean)} method.
 *
 * <p>The provided repositories include:</p>
 * <ul>
 *   <li>{@link FigureCategoryRepository} - Repository for figure categories.</li>
 *   <li>{@link FigureRepository} - Repository for figures.</li>
 *   <li>{@link CostumerRepository} - Repository for customers.</li>
 *   <li>{@link ShowRequestRepository} - Repository for show requests.</li>
 *   <li>{@link AuthenticationRepository} - Repository for authentication.</li>
 *   <li>{@link DroneModelRepository} - Repository for drone models.</li>
 * </ul>
 *
 * The class also provides methods for injecting mock repositories for unit testing purposes.
 *
 * <p>Example usage:</p>
 * <pre>
 * RepositoryProvider.setUseInMemory(true); // Use in-memory repositories
 * CostumerRepository costumerRepo = RepositoryProvider.costumerRepository();
 * </pre>
 *
 * @see FigureCategoryRepository
 * @see FigureRepository
 * @see CostumerRepository
 * @see ShowRequestRepository
 * @see AuthenticationRepository
 * @see DroneModelRepository
 */
public class RepositoryProvider {
    private static FigureCategoryRepository figureCategoryRepository;
    private static FigureRepository figureRepository;
    private static CostumerRepository costumerRepository;
    private static ShowRequestRepository showRequestRepository;
    private static AuthenticationRepository authenticationRepository;
    private static DroneModelRepository droneModelRepository;
    private static DroneRepository droneRepository;
    private static UserRepository userRepository;
    private static CustomerRepresentativeRepository customerRepresentativeRepository;


    private static AuditLoggerService auditLoggerService;
    private static AuditLogRepository auditLogRepository;

    /**
     * Flag indicating whether in-memory or JPA-based repositories should be used.
     */
    @Setter
    private static boolean useInMemory = true;

    /**
     * Checks if in-memory repositories should be used or JPA-based repositories.
     *
     * @return {@code true} if in-memory repositories are used; {@code false} otherwise.
     */
    public static boolean isInMemory() {
        return useInMemory;
    }



    public static void initializeAuditLogger(boolean inMemory) {
        if (inMemory) {
            auditLogRepository = new InMemoryAuditLogRepository();
        } else {
            auditLogRepository = new AuditLogJPAImpl();
        }
        auditLoggerService = new AuditLoggerService(auditLogRepository);
    }
    /**
     * Retrieves the figure category repository.
     *
     * @return The figure category repository.
     */
    public static FigureCategoryRepository figureCategoryRepository() {
        if (figureCategoryRepository == null) {
            if (isInMemory()) {
                figureCategoryRepository = new InMemoryFigureCategoryRepository(auditLoggerService);
            } else {
                figureCategoryRepository = new FigureCategoryJPAImpl(auditLoggerService);
            }
        }
        return figureCategoryRepository;
    }

    /**
     * Retrieves the figure repository.
     *
     * @return The figure repository.
     */
    public static FigureRepository figureRepository() {
        if (figureRepository == null) {
            if (isInMemory()) {
                figureRepository = new InMemoryFigureRepository(auditLoggerService);
            } else {
                figureRepository = new FigureRepositoryJPAImpl();
            }
        }
        return figureRepository;
    }

    /**
     * Retrieves the customer repository.
     *
     * @return The customer repository.
     */
    public static CostumerRepository costumerRepository() {
        if (costumerRepository == null) {
            if (isInMemory()) {
                costumerRepository = new InMemoryCustomerRepository();
            } else {
                costumerRepository = new CostumerJPAImpl();
            }
        }
        return costumerRepository;
    }

    /**
     * Retrieves the show request repository.
     *
     * @return The show request repository.
     */
    public static ShowRequestRepository showRequestRepository() {
        if (showRequestRepository == null) {
            if (isInMemory()) {
                showRequestRepository = new InMemoryShowRequestRepository();
            } else {
                showRequestRepository = new ShowRequestJPAImpl();
            }
        }
        return showRequestRepository;
    }

    /**
     * Retrieves the authentication repository.
     *
     * @return The authentication repository.
     */
    public static AuthenticationRepository authenticationRepository() {
        if (authenticationRepository == null) {
            if (isInMemory()) {
                authenticationRepository = new InMemoryAuthenticationRepository();
            } else {
                authenticationRepository = new AuthenticationRepositoryJPAImpl();
            }
        }
        return authenticationRepository;
    }

    /**
     * Retrieves the drone model repository.
     *
     * @return The drone model repository.
     */
    public static DroneModelRepository droneModelRepository() {
        if (droneModelRepository == null) {
            if (isInMemory()) {
                droneModelRepository = new InMemoryDroneModelRepository();
            } else {
                droneModelRepository = new CreateDroneModelJPAImpl();
            }
        }
        return droneModelRepository;
    }

    public static DroneRepository droneRepository() {
        if (droneRepository == null) {
            if (isInMemory()) {
                droneRepository = new InMemoryDroneRepository();
            } else {
                droneRepository = new DroneJPAImpl();
            }
        }
        return droneRepository;
    }


    /**
     * Dependency injection for the figure category repository (for testing purposes).
     *
     * @param mockRepo The mock repository to inject.
     */
    public static void injectFigureCategoryRepository(FigureCategoryRepository mockRepo) {
        figureCategoryRepository = mockRepo;
    }

    /**
     * Dependency injection for the authentication repository (for testing purposes).
     *
     * @param mockRepo The mock repository to inject.
     */
    public static void injectAuthenticationRepository(AuthenticationRepository mockRepo) {
        authenticationRepository = mockRepo;
    }

    public static UserRepository userRepository() {
        if (userRepository == null) {
            if (isInMemory()) {
                userRepository = (UserRepository) authenticationRepository();
            } else {
                userRepository = new UserRepositoryJPAImpl();
            }
        }
        return userRepository;
    }

    public static CustomerRepresentativeRepository customerRepresentativeRepository() {
        if (customerRepresentativeRepository == null) {
            if (isInMemory()) {
                throw new UnsupportedOperationException("In-memory not implemented yet.");
            } else {
                customerRepresentativeRepository = new CustomerRepresentativeRepositoryJPAImpl();
            }
        }
        return customerRepresentativeRepository;
    }

}