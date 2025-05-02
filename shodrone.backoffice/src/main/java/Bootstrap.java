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

    Costumer customer1 = new Costumer(
            Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Rua Brigadeiro", "Porto", "4440-778", "Portugal")
    );

    Costumer customer2 = new Costumer(
            Name.valueOf("Maria", "Silva"),
            EmailAddress.valueOf("maria.silva@shodrone.app"),
            new PhoneNumber("923456789"),
            new NIF("286500850"),
            new Address("Rua das Flores", "Lisboa", "1100-045", "Portugal")
    );

    Costumer customer3 = new Costumer(
            Name.valueOf("Carlos", "Ferreira"),
            EmailAddress.valueOf("carlos.ferreira@shodrone.app"),
            new PhoneNumber("934567890"),
            new NIF("248367080"),
            new Address("Avenida Central", "Braga", "4700-123", "Portugal")
    );


    Figure figure1 = new Figure(
            1L,
            "Aviao",
            new Description("NAO SEI O Q ESCREVER"),
            new Version(),
            new FigureCategory(
                    new domain.valueObjects.Name("Joao Mario"),
                    new Description("NAO SEI O Q ESCREVER"),
                    new Email("oxuvaiimplementar@shodrone.app")
            ),
            FigureAvailability.PUBLIC,
            FigureStatus.ACTIVE,
            customer1
    );

    Figure figure2 = new Figure(
            2L,
            "Circulo",
            new Description("NAO SEI O Q ESCREVER"),
            new Version(),
            new FigureCategory(new domain.valueObjects.Name("Joao Mario"), new Description("NAO SEI O Q ESCREVER"), new Email("oxuvaiimplementar@shodrone.app")),
            FigureAvailability.EXCLUSIVE,
            FigureStatus.ACTIVE,
            customer1
    );

    Figure figure3 = new Figure(
            3L,
            "Quadrado",
            new Description("NAO SEI O Q ESCREVER"),
            new Version(),
            new FigureCategory(new domain.valueObjects.Name("Joao Mario"), new Description("NAO SEI O Q ESCREVER"), new Email("oxuvaiimplementar@shodrone.app")),
            FigureAvailability.EXCLUSIVE,
            FigureStatus.ACTIVE,
            customer1
    );

    Figure figure4 = new Figure(
            4L,
            "Retangulo",
            new Description("NAO SEI O Q ESCREVER"),
            new Version(),
            new FigureCategory(new domain.valueObjects.Name("Joao Mario"), new Description("NAO SEI O Q ESCREVER"), new Email("oxuvaiimplementar@shodrone.app")),
            FigureAvailability.PUBLIC,
            FigureStatus.ACTIVE,
            customer1
    );

    public void run() {
        addUsers();
//        RepositoryProvider.costumerRepository().saveInStore(customer1,customer1.nif());
//        RepositoryProvider.costumerRepository().saveInStore(customer2,customer2.nif());
//        RepositoryProvider.costumerRepository().saveInStore(customer3,customer3.nif());
//        RepositoryProvider.figureRepository().save(figure1, figure1.identity());
//        RepositoryProvider.figureRepository().save(figure2, figure2.identity());
//        RepositoryProvider.figureRepository().save(figure3, figure3.identity());
//        RepositoryProvider.figureRepository().save(figure4, figure4.identity());
    }

    protected static void inputAppInformation() {
        //Serialization serialization = new Serialization();
        //serialization.serializeRepositoriesInput();
    }

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
        repo.addUserWithRole("Drone Technician x", "dronetech@shodrone.app", "Drone987^", AuthenticationController.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", AuthenticationController.ROLE_CRM_COLLABORATOR);
    }


}