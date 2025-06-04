import constants.Roles;
import domain.entity.*;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import persistence.RepositoryProvider;
import persistence.AuthenticationRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Bootstrap implements Runnable {

    @Override
    public void run() {
        addUsers();
        addCategories();
        addCustomers();
        addFigures();
        addDroneModels();
        addDrones();
        //addRepresentatives();
        //addShowRequest();
        initLists();
        //addProposals();
    }


    // --- User Setup -----------------------------------------------------
    private void addUsers() {
        AuthenticationRepository repo = RepositoryProvider.authenticationRepository();

        repo.addUserRole(Roles.ROLE_ADMIN, "Administrator");
        repo.addUserRole(Roles.ROLE_CRM_MANAGER, "CRM Manager");
        repo.addUserRole(Roles.ROLE_CRM_COLLABORATOR, "CRM Collaborator");
        repo.addUserRole(Roles.ROLE_SHOW_DESIGNER, "Show Designer");
        repo.addUserRole(Roles.ROLE_CUSTOMER_REPRESENTATIVE, "Customer Representative");
        repo.addUserRole(Roles.ROLE_DRONE_TECH, "Drone Technician");

        repo.addUserWithRole("Administrator x", "admin@shodrone.app", "Admin123!", Roles.ROLE_ADMIN);
        repo.addUserWithRole("CRM Manager x", "crm_manager@shodrone.app", "CrmMan456@", Roles.ROLE_CRM_MANAGER);
        repo.addUserWithRole("CRM Collaborator x", "crm_collaborator@shodrone.app", "Colab789#", Roles.ROLE_CRM_COLLABORATOR);
        repo.addUserWithRole("Show Designer x", "show_designer@shodrone.app", "Show321$", Roles.ROLE_SHOW_DESIGNER);
        repo.addUserWithRole("Representative x", "representative@shodrone.app", "Repres654%", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("Drone Technician x", "dt@shodrone.app", "Xv1234!", Roles.ROLE_DRONE_TECH);
        repo.addUserWithRole("DroSDSDn x", "xu@shodrone.app", "XuTech159&", Roles.ROLE_CRM_COLLABORATOR);
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

    // --- Representative Setup -------------------------------------------------
    private void addRepresentatives() {
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeMicrosoft);
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeApple);
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeContinente);
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

    // --- Show Request Setup ---------------------------------------------------

    private void addShowRequest() {
        RepositoryProvider.showRequestRepository().saveInStore(showRequest1);
        RepositoryProvider.showRequestRepository().saveInStore(showRequest2);
        RepositoryProvider.showRequestRepository().saveInStore(showRequest3);
    }

    // --- Lists Setup ---------------------------------------------------

    private void initLists() {
        list1.add(figure1);

        list2.add(figure2);
        list2.add(figure3);

        list3.add(figure4);
        list3.add(figure5);
        list3.add(figure6);

        list4.add(figure7);
        list4.add(figure8);
        list4.add(figure9);
        list4.add(figure10);

        list5.add(figure11);
        list5.add(figure12);
        list5.add(figure13);
        list5.add(figure14);
        list5.add(figure15);

        list6.add(figure16);
        list6.add(figure17);
        list6.add(figure18);
        list6.add(figure19);
        list6.add(figure20);
    }

    // --- Show Proposals Setup ---------------------------------------------------
    private void addProposals() {
        RepositoryProvider.showProposalRepository().saveInStore(proposal1);
        RepositoryProvider.showProposalRepository().saveInStore(proposal2);
        RepositoryProvider.showProposalRepository().saveInStore(proposal3);
        RepositoryProvider.showProposalRepository().saveInStore(proposal4);
        RepositoryProvider.showProposalRepository().saveInStore(proposal5);
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
            new domain.valueObjects.Name("Microfost"),
            EmailAddress.valueOf("microsoft.SA@shodrone.app"),
            new PhoneNumber("912800777"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Costumer customer2 = new Costumer(
            new domain.valueObjects.Name("Apple"),
            EmailAddress.valueOf("apple.Store@shodrone.app"),
            new PhoneNumber("923456789"),
            new NIF("286500850"),
            new Address("Flower Street", "Lisbon", "1100-045", "Portugal")
    );

    private final Costumer customer3 = new Costumer(
            new domain.valueObjects.Name("Continente"),
            EmailAddress.valueOf("continente.SA@shodrone.app"),
            new PhoneNumber("934567890"),
            new NIF("248367080"),
            new Address("Central Avenue", "Braga", "4700-123", "Portugal")
    );

    // --- Representative ------------------------------------------------------
    private final CustomerRepresentative representativeMicrosoft = new CustomerRepresentative(foundCostumerByNIF(new NIF("123456789")), new domain.valueObjects.Name("Jorge Ubaldo"), new Email("jorgeUbaldo@shodrone.app"), new PhoneNumber("914861312"), "CEO");
    private final CustomerRepresentative representativeApple = new CustomerRepresentative(foundCostumerByNIF(new NIF("286500850")), new domain.valueObjects.Name("Francisco"), new Email("francisco@shodrone.app"), new PhoneNumber("912856060"), "CEO");
    private final CustomerRepresentative representativeContinente = new CustomerRepresentative(foundCostumerByNIF(new NIF("248367080")), new domain.valueObjects.Name("Paulo Mendes"), new Email("paulo@shodrone.app"), new PhoneNumber("912809789"), "CEO");

    // --- Figures --------------------------------------------------------
    private final Figure figure1 = new Figure(new domain.valueObjects.Name("Circle"), new Description("A perfect round shape"), 1L, category1, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"), customer1);
    private final Figure figure2 = new Figure(new domain.valueObjects.Name("Tree"), new Description("A tall plant with a trunk"), 3L, category3, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer1);
    private final Figure figure3 = new Figure(new domain.valueObjects.Name("Guitar"), new Description("A stringed musical instrument"), 7L, category7, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"), customer1);
    private final Figure figure4 = new Figure(new domain.valueObjects.Name("Rocket"), new Description("A vehicle propelled by rocket engines"), 9L, category9, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);
    private final Figure figure5 = new Figure(new domain.valueObjects.Name("Heart"), new Description("A classic symbol of love"), 14L, category14, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);
    private final Figure figure6 = new Figure(new domain.valueObjects.Name("Flower"), new Description("A colorful part of a plant"), 16L, category16, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"), customer1);
    private final Figure figure7 = new Figure(new domain.valueObjects.Name("Fish"), new Description("A typical sea fish"), 19L, category19, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer1);

    private final Figure figure8 = new Figure(new domain.valueObjects.Name("Robot"), new Description("A programmable machine"), 4L, category4, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"), customer2);
    private final Figure figure9 = new Figure(new domain.valueObjects.Name("Spiral"), new Description("A curve that winds around a center point"), 5L, category5, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure10 = new Figure(new domain.valueObjects.Name("Car"), new Description("A four-wheeled motor vehicle"), 10L, category10, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"), customer2);
    private final Figure figure11 = new Figure(new domain.valueObjects.Name("Pizza"), new Description("A slice of Italian pizza"), 11L, category11, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure12 = new Figure(new domain.valueObjects.Name("House"), new Description("A simple house with a roof"), 13L, category13, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"), customer2);
    private final Figure figure13 = new Figure(new domain.valueObjects.Name("Dragon"), new Description("A mythical fire-breathing creature"), 17L, category17, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure14 = new Figure(new domain.valueObjects.Name("Smiley"), new Description("A classic smiling emoji face"), 20L, category20, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);

    private final Figure figure15 = new Figure(new domain.valueObjects.Name("Airplane"), new Description("A fixed-wing flying vehicle"), 2L, category2, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure16 = new Figure(new domain.valueObjects.Name("Cat"), new Description("A domestic feline animal"), 6L, category6, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure17 = new Figure(new domain.valueObjects.Name("Football"), new Description("A classic soccer ball"), 8L, category8, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer3);
    private final Figure figure18 = new Figure(new domain.valueObjects.Name("Runner"), new Description("A person running"), 12L, category12, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);
    private final Figure figure19 = new Figure(new domain.valueObjects.Name("Christmas Tree"), new Description("A decorated pine for Christmas"), 18L, category18, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("input.txt"), customer3);
    private final Figure figure20 = new Figure(new domain.valueObjects.Name("Cloud"), new Description("A visible mass of condensed water vapor"), 15L, category15, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, new DSL("drones.txt"), customer3);

    // --- List Figures --------------------------------------------------------
    private final List<Figure> list1 = new LinkedList<>();
    private final List<Figure> list2 = new LinkedList<>();
    private final List<Figure> list3 = new LinkedList<>();
    private final List<Figure> list4 = new LinkedList<>();
    private final List<Figure> list5 = new LinkedList<>();
    private final List<Figure> list6 = new LinkedList<>();

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

    // --- Show Request -----------------------------------------------------------

    private final Description description1 = new Description("Amazing drone show!");
    private final Description description2 = new Description("Incredible light performance!");
    private final Description description3 = new Description("Breathtaking sky art!");

    private final Location location1 = new Location(new Address("Rua", "Porto", "4444-888", "Portugal"), 12, 12, "iasjdiasjdiasdjasid");
    private final Location location2 = new Location(new Address("Avenida", "Lisboa", "1111-222", "Portugal"), 34, 45, "localdesc2");
    private final Location location3 = new Location(new Address("Praça", "Coimbra", "3333-444", "Portugal"), 56, 78, "localdesc3");

    private final ShowRequest showRequest1 = new ShowRequest(2L,
            LocalDateTime.now(), ShowRequestStatus.PENDING,
            "crm_collaborator@shodrone.app", foundCostumerByNIF(new NIF("123456789")),
            Arrays.asList(figure1, figure2, figure3),
            description1, location1,
            LocalDateTime.now().plusHours(1),
            10,
            Duration.ofMinutes(15));

    private final ShowRequest showRequest2 = new ShowRequest(3L,
            LocalDateTime.now().minusDays(1), ShowRequestStatus.PENDING,
            "crm_collaborator@shodrone.app", foundCostumerByNIF(new NIF("286500850")),
            Arrays.asList(figure8, figure9),
            description2, location2,
            LocalDateTime.now().plusHours(2),
            20,
            Duration.ofMinutes(30));

    private final ShowRequest showRequest3 = new ShowRequest(4L,
            LocalDateTime.now().minusDays(2), ShowRequestStatus.PENDING,
            "crm_collaborator@shodrone.app", foundCostumerByNIF(new NIF("248367080")),
            Arrays.asList(figure15, figure16),
            description3, location3,
            LocalDateTime.now().plusDays(1),
            15,
            Duration.ofMinutes(45));


    // --- Proposals -----------------------------------------------------------
    private final ShowProposal proposal1 = new ShowProposal(showRequest1, null, list1,
            new Description("Description 1"),
            new Location(new Address("Street1", "City1", "4444-000", "Country1"), 60, 120, "Description"),
            LocalDateTime.now().plusDays(3L), 10, Duration.ofMinutes(60),
            "crm_collaborator@shodrone.app", LocalDateTime.now(), "Prop1");

    private final ShowProposal proposal2 = new ShowProposal(showRequest1, null, list2,
            new Description("Description 2"),
            new Location(new Address("Street2", "City2", "5555-111", "Country2"), 50, 100, "Another description"),
            LocalDateTime.now().plusDays(5L), 15, Duration.ofMinutes(45),
            "crm_collaborator@shodrone.app", LocalDateTime.now(), "Prop2");

    private final ShowProposal proposal3 = new ShowProposal(showRequest2, null, list3,
            new Description("Description 3"),
            new Location(new Address("Street3", "City3", "6666-222", "Country3"), 45, 150, "Yet another description"),
            LocalDateTime.now().plusDays(7L), 20, Duration.ofMinutes(90),
            "crm_collaborator@shodrone.app", LocalDateTime.now(), "Prop3");

    private final ShowProposal proposal4 = new ShowProposal(showRequest2, null, list4,
            new Description("Description 4"),
            new Location(new Address("Street4", "City4", "7777-333", "Country4"), 80, 130, "Different description"),
            LocalDateTime.now().plusDays(10L), 12, Duration.ofMinutes(75),
            "crm_collaborator@shodrone.app", LocalDateTime.now(), "Prop4");

    private final ShowProposal proposal5 = new ShowProposal(showRequest3, null, list5,
            new Description("Description 5"),
            new Location(new Address("Street5", "City5", "8888-444", "Country5"), 80, 110, "Some description here"),
            LocalDateTime.now().plusDays(12L), 18, Duration.ofMinutes(120),
            "crm_collaborator@shodrone.app", LocalDateTime.now(), "Prop5");

    // ---- -------------------
    private Costumer foundCostumerByNIF(NIF nif) {
        Optional<Costumer> result = RepositoryProvider.costumerRepository().findByNIF(nif);
        return result.orElse(null);
    }
}