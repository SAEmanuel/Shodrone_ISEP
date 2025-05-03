import controller.AuthenticationController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import persistence.RepositoryProvider;
import persistence.interfaces.AuthenticationRepository;
import authz.*;


public class Bootstrap implements Runnable {

    @Override
    public void run() {
        addUsers();
        addCategories();
        addCustomers();
        addFigures();
    }

    // --- User Setup -----------------------------------------------------
    private void addUsers() {
        AuthenticationRepository repo = RepositoryProvider.authenticationRepository();

        repo.addUserRole(AuthenticationController.ROLE_ADMIN, "Administrator");
        repo.addUserRole(AuthenticationController.ROLE_CRM_MANAGER, "CRM Manager");
        repo.addUserRole(AuthenticationController.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        repo.addUserRole(AuthenticationController.ROLE_SHOW_DESIGNER, "Show Designer");
        repo.addUserRole(AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        repo.addUserRole(AuthenticationController.ROLE_DRONE_TECH, "Drone Technician");

        repo.addUserWithRole("Administrator x", "admin@shodrone.app", "Admin123!", AuthenticationController.ROLE_ADMIN);
        repo.addUserWithRole("CRM Manager x", "crm_manager@shodrone.app", "CrmMan456@", AuthenticationController.ROLE_CRM_MANAGER);
        repo.addUserWithRole("CRM Collaborator x", "crm_collaborator@shodrone.app", "Colab789#", AuthenticationController.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Show Designer x", "show_designer@shodrone.app", "Show321$", AuthenticationController.ROLE_SHOW_DESIGNER);
        repo.addUserWithRole("Representative x", "representative@shodrone.app", "Repres654%", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        //repo.addUserWithRole("Drone Technician x", "dronetech@shodrone.app", "Drone987^", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", AuthenticationController.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Drone Technician - Staff", "xv@shodrone.app", "Xv1234!", AuthenticationController.ROLE_DRONE_TECH);
    }

    // --- Category Setup -------------------------------------------------
    private void addCategories() {
        RepositoryProvider.figureCategoryRepository().save(category1);
        RepositoryProvider.figureCategoryRepository().save(category2);
        RepositoryProvider.figureCategoryRepository().save(category3);
    }

    // --- Customer Setup -------------------------------------------------
    private void addCustomers() {
        RepositoryProvider.costumerRepository().saveInStore(customer1, customer1.nif());
        RepositoryProvider.costumerRepository().saveInStore(customer2, customer2.nif());
        RepositoryProvider.costumerRepository().saveInStore(customer3, customer3.nif());
    }

    // --- Figure Setup ---------------------------------------------------
    private void addFigures() {
        RepositoryProvider.figureRepository().save(figure1);
        RepositoryProvider.figureRepository().save(figure2);
        RepositoryProvider.figureRepository().save(figure3);
        RepositoryProvider.figureRepository().save(figure4);
    }

    // --- Categories -----------------------------------------------------
    private final FigureCategory category1 = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    private final FigureCategory category2 = new FigureCategory(
            new domain.valueObjects.Name("Aviation"),
            new Description("Aircraft-related models"),
            new Email("show_designer@shodrone.app")
    );

    private final FigureCategory category3 = new FigureCategory(
            new domain.valueObjects.Name("Miscellaneous"),
            new Description("Other varied models"),
            new Email("show_designer@shodrone.app")
    );

    // --- Customers ------------------------------------------------------
    private final Costumer customer1 = new Costumer(
            Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Costumer customer2 = new Costumer(
            Name.valueOf("Romeu", "Mendes"),
            EmailAddress.valueOf("maria.silva@shodrone.app"),
            new PhoneNumber("923456789"),
            new NIF("286500850"),
            new Address("Flower Street", "Lisbon", "1100-045", "Portugal")
    );

    private final Costumer customer3 = new Costumer(
            Name.valueOf("Paulo", "Xu"),
            EmailAddress.valueOf("carlos.ferreira@shodrone.app"),
            new PhoneNumber("934567890"),
            new NIF("248367080"),
            new Address("Central Avenue", "Braga", "4700-123", "Portugal")
    );

    // --- Figures --------------------------------------------------------
    private final Figure figure1 = new Figure("Airplane", new Description("Airplane figure"), (long) 1.2,
            category2, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, customer2
    );

    private final Figure figure2 = new Figure("Circle", new Description("Circular figure"), (long) 1.2,
            category1, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, customer1
    );

    private final Figure figure3 = new Figure("Square", new Description("Square figure"), (long) 1.2,
            category1, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, customer1
    );

    private final Figure figure4 = new Figure("Rectangle", new Description("Rectangular figure"), (long) 1.2,
            category3, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, customer1
    );
}