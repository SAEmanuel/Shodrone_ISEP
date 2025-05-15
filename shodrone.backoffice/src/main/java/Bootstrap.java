import controller.authz.AuthenticationController;
import domain.entity.*;
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
        addDroneModels();
        addDrones();
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
        repo.addUserWithRole("Drone Technician x", "dt@shodrone.app", "Xv1234!", AuthenticationController.ROLE_DRONE_TECH);
        repo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", AuthenticationController.ROLE_CRM_COLLABORATOR);
    }

    // --- Category Setup -------------------------------------------------
    private void addCategories() {
        RepositoryProvider.figureCategoryRepository().save(category1);
        RepositoryProvider.figureCategoryRepository().save(category2);
        RepositoryProvider.figureCategoryRepository().save(category3);
        RepositoryProvider.figureCategoryRepository().save(category4);
        RepositoryProvider.figureCategoryRepository().save(category5);
        RepositoryProvider.figureCategoryRepository().save(category6);
        RepositoryProvider.figureCategoryRepository().save(category7);
        RepositoryProvider.figureCategoryRepository().save(category8);
        RepositoryProvider.figureCategoryRepository().save(category9);
        RepositoryProvider.figureCategoryRepository().save(category10);
        RepositoryProvider.figureCategoryRepository().save(category11);
        RepositoryProvider.figureCategoryRepository().save(category12);
        RepositoryProvider.figureCategoryRepository().save(category13);
        RepositoryProvider.figureCategoryRepository().save(category14);
        RepositoryProvider.figureCategoryRepository().save(category15);
        RepositoryProvider.figureCategoryRepository().save(category16);
        RepositoryProvider.figureCategoryRepository().save(category17);
        RepositoryProvider.figureCategoryRepository().save(category18);
        RepositoryProvider.figureCategoryRepository().save(category19);
        RepositoryProvider.figureCategoryRepository().save(category20);
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
        RepositoryProvider.figureRepository().save(figure5);
        RepositoryProvider.figureRepository().save(figure6);
        RepositoryProvider.figureRepository().save(figure7);
        RepositoryProvider.figureRepository().save(figure8);
        RepositoryProvider.figureRepository().save(figure9);
        RepositoryProvider.figureRepository().save(figure10);
        RepositoryProvider.figureRepository().save(figure11);
        RepositoryProvider.figureRepository().save(figure12);
        RepositoryProvider.figureRepository().save(figure13);
        RepositoryProvider.figureRepository().save(figure14);
        RepositoryProvider.figureRepository().save(figure15);
        RepositoryProvider.figureRepository().save(figure16);
        RepositoryProvider.figureRepository().save(figure17);
        RepositoryProvider.figureRepository().save(figure18);
        RepositoryProvider.figureRepository().save(figure19);
        RepositoryProvider.figureRepository().save(figure20);
    }

    // --- Drone Model Setup ---------------------------------------------------
    private void addDroneModels() {
        RepositoryProvider.droneModelRepository().save(droneModel1);
        RepositoryProvider.droneModelRepository().save(droneModel2);
        RepositoryProvider.droneModelRepository().save(droneModel3);
        RepositoryProvider.droneModelRepository().save(droneModel4);
        RepositoryProvider.droneModelRepository().save(droneModel5);
    }

    // --- Drone Setup ---------------------------------------------------
    private void addDrones() {
        RepositoryProvider.droneRepository().save(drone1);
        RepositoryProvider.droneRepository().save(drone2);
        RepositoryProvider.droneRepository().save(drone3);
        RepositoryProvider.droneRepository().save(drone4);
        RepositoryProvider.droneRepository().save(drone5);
        RepositoryProvider.droneRepository().save(drone6);
        RepositoryProvider.droneRepository().save(drone7);
        RepositoryProvider.droneRepository().save(drone8);
        RepositoryProvider.droneRepository().save(drone9);
        RepositoryProvider.droneRepository().save(drone10);
        RepositoryProvider.droneRepository().save(drone11);
        RepositoryProvider.droneRepository().save(drone12);
        RepositoryProvider.droneRepository().save(drone13);
        RepositoryProvider.droneRepository().save(drone14);
        RepositoryProvider.droneRepository().save(drone15);
        RepositoryProvider.droneRepository().save(drone16);
        RepositoryProvider.droneRepository().save(drone17);
        RepositoryProvider.droneRepository().save(drone18);
        RepositoryProvider.droneRepository().save(drone19);
        RepositoryProvider.droneRepository().save(drone20);
    }



    // --- Categories -----------------------------------------------------
    private final FigureCategory category1 = new FigureCategory(new domain.valueObjects.Name("Geometry"), new Description("Geometric shapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category2 = new FigureCategory(new domain.valueObjects.Name("Aviation"), new Description("Aircraft and flying objects"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category3 = new FigureCategory(new domain.valueObjects.Name("Nature"), new Description("Natural elements and landscapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category4 = new FigureCategory(new domain.valueObjects.Name("Technology"), new Description("Tech and gadgets"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category5 = new FigureCategory(new domain.valueObjects.Name("Abstract"), new Description("Abstract forms"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category6 = new FigureCategory(new domain.valueObjects.Name("Animals"), new Description("Animal shapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category7 = new FigureCategory(new domain.valueObjects.Name("Music"), new Description("Musical symbols and instruments"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category8 = new FigureCategory(new domain.valueObjects.Name("Sports"), new Description("Sports-related figures"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category9 = new FigureCategory(new domain.valueObjects.Name("Space"), new Description("Space and astronomy"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category10 = new FigureCategory(new domain.valueObjects.Name("Transport"), new Description("Vehicles and means of transport"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category11 = new FigureCategory(new domain.valueObjects.Name("Food"), new Description("Food and drink"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category12 = new FigureCategory(new domain.valueObjects.Name("People"), new Description("Human figures and actions"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category13 = new FigureCategory(new domain.valueObjects.Name("Buildings"), new Description("Buildings and architecture"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category14 = new FigureCategory(new domain.valueObjects.Name("Symbols"), new Description("Common symbols"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category15 = new FigureCategory(new domain.valueObjects.Name("Weather"), new Description("Weather phenomena"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category16 = new FigureCategory(new domain.valueObjects.Name("Plants"), new Description("Plant life"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category17 = new FigureCategory(new domain.valueObjects.Name("Fantasy"), new Description("Fantasy and mythical creatures"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category18 = new FigureCategory(new domain.valueObjects.Name("Holiday"), new Description("Holiday and festive shapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category19 = new FigureCategory(new domain.valueObjects.Name("Ocean"), new Description("Sea and ocean life"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category20 = new FigureCategory(new domain.valueObjects.Name("Emojis"), new Description("Emoji-inspired figures"), new Email("show_designer@shodrone.app"));

    // --- Customers ------------------------------------------------------
    private final Costumer customer1 = new Costumer(
            Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Costumer customer2 = new Costumer(
            Name.valueOf("Maria", "Silva"),
            EmailAddress.valueOf("maria.silva@shodrone.app"),
            new PhoneNumber("923456789"),
            new NIF("286500850"),
            new Address("Flower Street", "Lisbon", "1100-045", "Portugal")
    );

    private final Costumer customer3 = new Costumer(
            Name.valueOf("Carlos", "Ferreira"),
            EmailAddress.valueOf("carlos.ferreira@shodrone.app"),
            new PhoneNumber("934567890"),
            new NIF("248367080"),
            new Address("Central Avenue", "Braga", "4700-123", "Portugal")
    );

    // --- Figures --------------------------------------------------------
    private final Figure figure1  = new Figure(new domain.valueObjects.Name("Circle"),         new Description("A perfect round shape"),                   1L,  category1,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"),  customer1);
    private final Figure figure2  = new Figure(new domain.valueObjects.Name("Tree"),           new Description("A tall plant with a trunk"),              3L,  category3,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer1);
    private final Figure figure3  = new Figure(new domain.valueObjects.Name("Guitar"),         new Description("A stringed musical instrument"),           7L,  category7,  FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"),  customer1);
    private final Figure figure4  = new Figure(new domain.valueObjects.Name("Rocket"),         new Description("A vehicle propelled by rocket engines"),   9L,  category9,  FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);
    private final Figure figure5 = new Figure(new domain.valueObjects.Name("Heart"),          new Description("A classic symbol of love"),               14L, category14, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);
    private final Figure figure6 = new Figure(new domain.valueObjects.Name("Flower"),         new Description("A colorful part of a plant"),             16L, category16, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"),  customer1);
    private final Figure figure7 = new Figure(new domain.valueObjects.Name("Fish"),           new Description("A typical sea fish"),                     19L, category19, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);

    private final Figure figure8  = new Figure(new domain.valueObjects.Name("Robot"),          new Description("A programmable machine"),                 4L,  category4,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"),  customer2);
    private final Figure figure9  = new Figure(new domain.valueObjects.Name("Spiral"),         new Description("A curve that winds around a center point"), 5L,  category5,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure10 = new Figure(new domain.valueObjects.Name("Car"),            new Description("A four-wheeled motor vehicle"),           10L, category10, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"),  customer2);
    private final Figure figure11 = new Figure(new domain.valueObjects.Name("Pizza"),          new Description("A slice of Italian pizza"),               11L, category11, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure12 = new Figure(new domain.valueObjects.Name("House"),          new Description("A simple house with a roof"),             13L, category13, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"),  customer2);
    private final Figure figure13 = new Figure(new domain.valueObjects.Name("Dragon"),         new Description("A mythical fire-breathing creature"),     17L, category17, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure14 = new Figure(new domain.valueObjects.Name("Smiley"),         new Description("A classic smiling emoji face"),           20L, category20, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);

    private final Figure figure15  = new Figure(new domain.valueObjects.Name("Airplane"),       new Description("A fixed-wing flying vehicle"),            2L,  category2,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure16  = new Figure(new domain.valueObjects.Name("Cat"),            new Description("A domestic feline animal"),               6L,  category6,  FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure17  = new Figure(new domain.valueObjects.Name("Football"),       new Description("A classic soccer ball"),                  8L,  category8,  FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer3);
    private final Figure figure18 = new Figure(new domain.valueObjects.Name("Runner"),         new Description("A person running"),                       12L, category12, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure19 = new Figure(new domain.valueObjects.Name("Christmas Tree"), new Description("A decorated pine for Christmas"),         18L, category18, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"),  customer3);
    private final Figure figure20 = new Figure(new domain.valueObjects.Name("Cloud"),          new Description("A visible mass of condensed water vapor"),15L, category15, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer3);

    // --- Drone Models ------------------------------------------------------
    private final DroneModel droneModel1 = new DroneModel(
            new DroneModelID("Mavic3Classic"),
            new DroneName("DJI Mavic 3 Classic"),
            new Description("Flagship drone with Hasselblad camera, 46 minutes of flight time, wind resistance up to 12 m/s."),
            12
    );
    private final DroneModel droneModel2 = new DroneModel(
            new DroneModelID("Mini4Pro"),
            new DroneName("DJI Mini 4 Pro"),
            new Description("Compact and lightweight drone with omnidirectional sensors, 31 minutes of flight, ideal for creators on the move."),
            10
    );
    private final DroneModel droneModel3 = new DroneModel(
            new DroneModelID("Air3"),
            new DroneName("DJI Air 3"),
            new Description("Dual-camera system with 48MP photos, 46 minutes of flight, transmission range up to 20 km."),
            15
    );
    private final DroneModel droneModel4 = new DroneModel(
            new DroneModelID("Phantom4Pro"),
            new DroneName("DJI Phantom 4 Pro"),
            new Description("Professional drone with mechanical shutter, 30 minutes of flight, wind resistance up to 10 m/s."),
            10
    );
    private final DroneModel droneModel5 = new DroneModel(
            new DroneModelID("Shahed136"),
            new DroneName("Shahed 136"),
            new Description("Iranian loitering munition drone, used in military operations, long range and endurance."),
            20
    );

    // --- Drones -----------------------------------------------------------
    private final Drone drone1 = new Drone(new SerialNumber("SN-00001"), droneModel1);
    private final Drone drone2 = new Drone(new SerialNumber("SN-00002"), droneModel1);
    private final Drone drone3 = new Drone(new SerialNumber("SN-00003"), droneModel1);
    private final Drone drone4 = new Drone(new SerialNumber("SN-00004"), droneModel1);
    private final Drone drone5 = new Drone(new SerialNumber("SN-00005"), droneModel2);
    private final Drone drone6 = new Drone(new SerialNumber("SN-00006"), droneModel2);
    private final Drone drone7 = new Drone(new SerialNumber("SN-00007"), droneModel2);
    private final Drone drone8 = new Drone(new SerialNumber("SN-00008"), droneModel2);
    private final Drone drone9 = new Drone(new SerialNumber("SN-00009"), droneModel3);
    private final Drone drone10 = new Drone(new SerialNumber("SN-00010"), droneModel3);
    private final Drone drone11 = new Drone(new SerialNumber("SN-00011"), droneModel3);
    private final Drone drone12 = new Drone(new SerialNumber("SN-00012"), droneModel3);
    private final Drone drone13 = new Drone(new SerialNumber("SN-00013"), droneModel4);
    private final Drone drone14 = new Drone(new SerialNumber("SN-00014"), droneModel4);
    private final Drone drone15 = new Drone(new SerialNumber("SN-00015"), droneModel4);
    private final Drone drone16 = new Drone(new SerialNumber("SN-00016"), droneModel4);
    private final Drone drone17 = new Drone(new SerialNumber("SN-00017"), droneModel5);
    private final Drone drone18 = new Drone(new SerialNumber("SN-00018"), droneModel5);
    private final Drone drone19 = new Drone(new SerialNumber("SN-00019"), droneModel5);
    private final Drone drone20 = new Drone(new SerialNumber("SN-00020"), droneModel5);

}