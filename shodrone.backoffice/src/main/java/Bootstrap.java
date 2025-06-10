import constants.Roles;
import domain.entity.*;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import persistence.RepositoryProvider;
import persistence.AuthenticationRepository;
import proposal_template.validators.TemplatePlugin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class Bootstrap implements Runnable {
    private final boolean useInMemory;

    public Bootstrap(boolean useInMemory) {
        this.useInMemory = useInMemory;
    }

    @Override
    public void run() {
        addUsers();
        addCategories();
        addCustomers();
        addRepresentatives();
        addFigures();
        addDroneModels();
        addDrones();
        addShowRequests();
        addTemplates();
        addProposals();
        addMaintenanceTypes();
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
    }

    // --- Customer Setup -------------------------------------------------
    private void addCustomers() {
        RepositoryProvider.costumerRepository().saveInStore(customer1, customer1.nif());
        RepositoryProvider.costumerRepository().saveInStore(customer2, customer2.nif());
        RepositoryProvider.costumerRepository().saveInStore(customer3, customer3.nif());
    }

    // --- Representative Setup -------------------------------------------------
    private void addRepresentatives() {
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeMicrosoft());
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeApple());
        RepositoryProvider.customerRepresentativeRepository().saveInStore(representativeContinente());

        AuthenticationRepository repo = RepositoryProvider.authenticationRepository();
        repo.addUserWithRole("Jorge-Microsoft", "jorgeUbaldo@shodrone.app", "Jorge123#", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("Francisco-Apple", "francisco@shodrone.app", "Franc123#", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
        repo.addUserWithRole("Paulo-Continente", "paulo@shodrone.app", "Paulo123#", Roles.ROLE_CUSTOMER_REPRESENTATIVE);
    }

    // --- Figure Setup ---------------------------------------------------
    private void addFigures() {
        RepositoryProvider.figureRepository().save(figure1);
        RepositoryProvider.figureRepository().save(figure2);
        RepositoryProvider.figureRepository().save(figure3);
        RepositoryProvider.figureRepository().save(figure4);
        RepositoryProvider.figureRepository().save(figure5);
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
        RepositoryProvider.droneRepository().save(drone21);
        RepositoryProvider.droneRepository().save(drone22);
        RepositoryProvider.droneRepository().save(drone23);
        RepositoryProvider.droneRepository().save(drone24);
        RepositoryProvider.droneRepository().save(drone25);
        RepositoryProvider.droneRepository().save(drone26);
        RepositoryProvider.droneRepository().save(drone27);
        RepositoryProvider.droneRepository().save(drone28);
        RepositoryProvider.droneRepository().save(drone29);
        RepositoryProvider.droneRepository().save(drone30);
        RepositoryProvider.droneRepository().save(drone31);
        RepositoryProvider.droneRepository().save(drone32);
        RepositoryProvider.droneRepository().save(drone33);
        RepositoryProvider.droneRepository().save(drone34);
        RepositoryProvider.droneRepository().save(drone35);
        RepositoryProvider.droneRepository().save(drone36);
        RepositoryProvider.droneRepository().save(drone37);
        RepositoryProvider.droneRepository().save(drone38);
        RepositoryProvider.droneRepository().save(drone39);
        RepositoryProvider.droneRepository().save(drone40);
        RepositoryProvider.droneRepository().save(drone41);
        RepositoryProvider.droneRepository().save(drone42);
        RepositoryProvider.droneRepository().save(drone43);
        RepositoryProvider.droneRepository().save(drone44);
        RepositoryProvider.droneRepository().save(drone45);
        RepositoryProvider.droneRepository().save(drone46);
        RepositoryProvider.droneRepository().save(drone47);
        RepositoryProvider.droneRepository().save(drone48);
        RepositoryProvider.droneRepository().save(drone49);
        RepositoryProvider.droneRepository().save(drone50);
        RepositoryProvider.droneRepository().save(drone51);
        RepositoryProvider.droneRepository().save(drone52);
        RepositoryProvider.droneRepository().save(drone53);
        RepositoryProvider.droneRepository().save(drone54);
        RepositoryProvider.droneRepository().save(drone55);
        RepositoryProvider.droneRepository().save(drone56);
        RepositoryProvider.droneRepository().save(drone57);
        RepositoryProvider.droneRepository().save(drone58);
        RepositoryProvider.droneRepository().save(drone59);
        RepositoryProvider.droneRepository().save(drone60);
    }

    // --- Maintenance Type Setup ---------------------------------------------------
    private void addMaintenanceTypes() {
        RepositoryProvider.maintenanceTypeRepository().save(maintenanceType1);
        RepositoryProvider.maintenanceTypeRepository().save(maintenanceType2);
        RepositoryProvider.maintenanceTypeRepository().save(maintenanceType3);
        RepositoryProvider.maintenanceTypeRepository().save(maintenanceType4);
        RepositoryProvider.maintenanceTypeRepository().save(maintenanceType5);
    }


    // --- Show Proposal Templates Setup ---------------------------------------------------
    private void addTemplates() {
        RepositoryProvider.proposalTemplateRepository().save(templatePT);
        RepositoryProvider.proposalTemplateRepository().save(templateEN);
    }




    // --- Categories -----------------------------------------------------
    private final FigureCategory category1 = new FigureCategory(new domain.valueObjects.Name("Geometry"), new Description("Geometric shapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category2 = new FigureCategory(new domain.valueObjects.Name("Aviation"), new Description("Aircraft and flying objects"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category3 = new FigureCategory(new domain.valueObjects.Name("Nature"), new Description("Natural elements and landscapes"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category4 = new FigureCategory(new domain.valueObjects.Name("Technology"), new Description("Tech and gadgets"), new Email("show_designer@shodrone.app"));
    private final FigureCategory category5 = new FigureCategory(new domain.valueObjects.Name("Abstract"), new Description("Abstract forms"), new Email("show_designer@shodrone.app"));

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
    private CustomerRepresentative representativeMicrosoft() {
        if (!useInMemory)
            return new CustomerRepresentative(foundCostumerByNIF(new NIF("123456789")), new domain.valueObjects.Name("Jorge Ubaldo"), new Email("jorgeUbaldo@shodrone.app"), new PhoneNumber("914861312"), "CEO");
        else
            return new CustomerRepresentative(customer1, new domain.valueObjects.Name("Jorge Ubaldo"), new Email("jorgeUbaldo@shodrone.app"), new PhoneNumber("914861312"), "CEO");
    }

    private CustomerRepresentative representativeApple() {
        if (!useInMemory)
            return new CustomerRepresentative(foundCostumerByNIF(new NIF("286500850")), new domain.valueObjects.Name("Francisco"), new Email("francisco@shodrone.app"), new PhoneNumber("912856060"), "CEO");
        else
            return new CustomerRepresentative(customer2, new domain.valueObjects.Name("Francisco"), new Email("francisco@shodrone.app"), new PhoneNumber("912856060"), "CEO");
    }

    private CustomerRepresentative representativeContinente() {
        if (!useInMemory)
            return new CustomerRepresentative(foundCostumerByNIF(new NIF("248367080")), new domain.valueObjects.Name("Paulo Mendes"), new Email("paulo@shodrone.app"), new PhoneNumber("912809789"), "CEO");
        else
            return new CustomerRepresentative(customer3, new domain.valueObjects.Name("Paulo Mendes"), new Email("paulo@shodrone.app"), new PhoneNumber("912809789"), "CEO");
    }
    // --- Figures --------------------------------------------------------
    private final Figure figure1 = new Figure(new domain.valueObjects.Name("Circle"), new Description("A perfect round shape"), 1L, category1, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"), customer1);
    private final Figure figure2 = new Figure(new domain.valueObjects.Name("Tree"), new Description("A tall plant with a trunk"), 3L, category3, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer1);
    private final Figure figure3 = new Figure(new domain.valueObjects.Name("Robot"), new Description("A programmable machine"), 4L, category4, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("input.txt"), customer2);
    private final Figure figure4 = new Figure(new domain.valueObjects.Name("Spiral"), new Description("A curve that winds around a center point"), 5L, category5, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.txt"), customer2);
    private final Figure figure5 = new Figure(new domain.valueObjects.Name("Airplane"), new Description("A fixed-wing flying vehicle"), 2L, category2, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new DSL("drones.pdf"), customer3);



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
    private final Drone drone21 = new Drone(new SerialNumber("SN-00021"), droneModel1);
    private final Drone drone22 = new Drone(new SerialNumber("SN-00022"), droneModel2);
    private final Drone drone23 = new Drone(new SerialNumber("SN-00023"), droneModel3);
    private final Drone drone24 = new Drone(new SerialNumber("SN-00024"), droneModel4);
    private final Drone drone25 = new Drone(new SerialNumber("SN-00025"), droneModel5);
    private final Drone drone26 = new Drone(new SerialNumber("SN-00026"), droneModel1);
    private final Drone drone27 = new Drone(new SerialNumber("SN-00027"), droneModel2);
    private final Drone drone28 = new Drone(new SerialNumber("SN-00028"), droneModel3);
    private final Drone drone29 = new Drone(new SerialNumber("SN-00029"), droneModel4);
    private final Drone drone30 = new Drone(new SerialNumber("SN-00030"), droneModel5);
    private final Drone drone31 = new Drone(new SerialNumber("SN-00031"), droneModel1);
    private final Drone drone32 = new Drone(new SerialNumber("SN-00032"), droneModel2);
    private final Drone drone33 = new Drone(new SerialNumber("SN-00033"), droneModel3);
    private final Drone drone34 = new Drone(new SerialNumber("SN-00034"), droneModel4);
    private final Drone drone35 = new Drone(new SerialNumber("SN-00035"), droneModel5);
    private final Drone drone36 = new Drone(new SerialNumber("SN-00036"), droneModel1);
    private final Drone drone37 = new Drone(new SerialNumber("SN-00037"), droneModel2);
    private final Drone drone38 = new Drone(new SerialNumber("SN-00038"), droneModel3);
    private final Drone drone39 = new Drone(new SerialNumber("SN-00039"), droneModel4);
    private final Drone drone40 = new Drone(new SerialNumber("SN-00040"), droneModel5);
    private final Drone drone41 = new Drone(new SerialNumber("SN-00041"), droneModel1);
    private final Drone drone42 = new Drone(new SerialNumber("SN-00042"), droneModel2);
    private final Drone drone43 = new Drone(new SerialNumber("SN-00043"), droneModel3);
    private final Drone drone44 = new Drone(new SerialNumber("SN-00044"), droneModel4);
    private final Drone drone45 = new Drone(new SerialNumber("SN-00045"), droneModel5);
    private final Drone drone46 = new Drone(new SerialNumber("SN-00046"), droneModel1);
    private final Drone drone47 = new Drone(new SerialNumber("SN-00047"), droneModel2);
    private final Drone drone48 = new Drone(new SerialNumber("SN-00048"), droneModel3);
    private final Drone drone49 = new Drone(new SerialNumber("SN-00049"), droneModel4);
    private final Drone drone50 = new Drone(new SerialNumber("SN-00050"), droneModel5);
    private final Drone drone51 = new Drone(new SerialNumber("SN-00051"), droneModel1);
    private final Drone drone52 = new Drone(new SerialNumber("SN-00052"), droneModel2);
    private final Drone drone53 = new Drone(new SerialNumber("SN-00053"), droneModel3);
    private final Drone drone54 = new Drone(new SerialNumber("SN-00054"), droneModel4);
    private final Drone drone55 = new Drone(new SerialNumber("SN-00055"), droneModel5);
    private final Drone drone56 = new Drone(new SerialNumber("SN-00056"), droneModel1);
    private final Drone drone57 = new Drone(new SerialNumber("SN-00057"), droneModel2);
    private final Drone drone58 = new Drone(new SerialNumber("SN-00058"), droneModel3);
    private final Drone drone59 = new Drone(new SerialNumber("SN-00059"), droneModel4);
    private final Drone drone60 = new Drone(new SerialNumber("SN-00060"), droneModel5);

    // --- Maintenance Types ------------------------------------------------------

    private final MaintenanceType maintenanceType1 = new MaintenanceType(
            new MaintenanceTypeName("Battery Replacement"),
            new Description("Replace the drone's battery with a new unit to ensure full power and safe operation.")
    );

    private final MaintenanceType maintenanceType2 = new MaintenanceType(
            new MaintenanceTypeName("Propeller Check"),
            new Description("Inspect and replace damaged or worn propellers to ensure stable flight.")
    );

    private final MaintenanceType maintenanceType3 = new MaintenanceType(
            new MaintenanceTypeName("Firmware Update"),
            new Description("Update the drone's firmware to the latest version to improve performance and security.")
    );

    private final MaintenanceType maintenanceType4 = new MaintenanceType(
            new MaintenanceTypeName("Gimbal Calibration"),
            new Description("Recalibrate the gimbal to ensure smooth video capture and camera stability.")
    );

    private final MaintenanceType maintenanceType5 = new MaintenanceType(
            new MaintenanceTypeName("Sensor Cleaning"),
            new Description("Clean all external sensors to maintain accurate obstacle detection and GPS positioning.")
    );


    // --- Show Request -----------------------------------------------------------
    public void addShowRequests() {
        RepositoryProvider.showRequestRepository().saveInStore(showRequest1());
        RepositoryProvider.showRequestRepository().saveInStore(showRequest2());
        RepositoryProvider.showRequestRepository().saveInStore(showRequest3());
    }

    private final Description description1 = new Description("Amazing drone show!");
    private final Description description2 = new Description("Incredible light performance!");
    private final Description description3 = new Description("Breathtaking sky art!");

    private final Location location1 = new Location(new Address("Rua", "Porto", "4444-888", "Portugal"), 12, 12, "Near coast");
    private final Location location2 = new Location(new Address("Avenida", "Lisboa", "1111-222", "Portugal"), 34, 45, "Near Apple Building");
    private final Location location3 = new Location(new Address("Praça", "Coimbra", "3333-444", "Portugal"), 56, 78, "Caracas Center");

    private ShowRequest showRequest1() {
        Costumer customer = !useInMemory ? foundCostumerByNIF(new NIF("123456789")) : customer1;
        return new ShowRequest(1L,
                LocalDateTime.now(), ShowRequestStatus.PENDING,
                "crm_collaborator@shodrone.app", customer,
                Arrays.asList(foundFigure(1L), foundFigure(2L), foundFigure(3L)),
                description1, location1,
                LocalDateTime.now().plusHours(1),
                10,
                Duration.ofMinutes(15));
    }

    private ShowRequest showRequest2() {
        Costumer customer = !useInMemory ? foundCostumerByNIF(new NIF("286500850")) : customer2;
        return new ShowRequest(2L,
                LocalDateTime.now().minusDays(1), ShowRequestStatus.PENDING,
                "crm_collaborator@shodrone.app", customer,
                Arrays.asList(foundFigure(8L), foundFigure(9L)),
                description2, location2,
                LocalDateTime.now().plusHours(2),
                20,
                Duration.ofMinutes(30));
    }

    private ShowRequest showRequest3() {
        Costumer customer = !useInMemory ? foundCostumerByNIF(new NIF("248367080")) : customer3;
        return new ShowRequest(3L,
                LocalDateTime.now().minusDays(2), ShowRequestStatus.PENDING,
                "crm_collaborator@shodrone.app", customer,
                Arrays.asList(foundFigure(15L), foundFigure(16L)),
                description3, location3,
                LocalDateTime.now().plusDays(1),
                15,
                Duration.ofMinutes(45));
    }

    // --- Templates -----------------------------------------------------------
    private final ProposalTemplate templatePT = new ProposalTemplate(
            new Name("Template PT"),
            new Description("Template simples e curto em português"),
            List.of(
                    "Exmos. Senhores ${customerName},",
                    "",
                    "Envio da sua proposta para o espetáculo de ${showDate}.",
                    "",
                    "Local: ${showLocation}",
                    "Duração: ${duration}",
                    "",
                    "Figuras apresentadas:",
                    "${figures}",
                    "",
                    "Modelos de drones utilizados:",
                    "${drones}",
                    "",
                    "Video preview do show: ${video}",
                    "",
                    "Obrigado pela sua preferência,",
                    "${manager}",
                    "",
                    "© 2025 Shodrone. Todos os direitos reservados."
            )
    );


    private final ProposalTemplate templateEN = new ProposalTemplate(
            new Name("Template EN"),
            new Description("Simple and short proposal template in English"),
            List.of(
                    "Dear ${customerName},",
                    "",
                    "Your show proposal for the show on ${showDate}.",
                    "",
                    "Location: ${showLocation}",
                    "Duration: ${duration}",
                    "",
                    "Presented figures:",
                    "${figures}",
                    "",
                    "Drone models used:",
                    "${drones}",
                    "",
                    "Video preview of the show: ${video}",
                    "",
                    "Thank you for your preference,",
                    "${manager}",
                    "",
                    "© 2025 Shodrone. All rights reserved."
            )
    );


    // --- Proposals -----------------------------------------------------------
    private ShowProposal proposal1() {
        Content content = new Content(
                foundCostumerByNIF(new NIF("123456789")),
                showRequest1().getShowDate(),
                showRequest1().getLocation(),
                showRequest1().getShowDuration(),
                proposal1Figures(), proposal1DroneModels(),
                "crm_collaborator@shodrone.app");

        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);

        List<String> filled = TemplatePlugin.replacePlaceholders(RepositoryProvider.proposalTemplateRepository().findByName("Template EN").get().text(), placeholders);
        content.changeText(filled);


        ShowProposal proposal = new ShowProposal(new Name("Proposal One"), showRequest1(), RepositoryProvider.proposalTemplateRepository().findByName("Template EN").get(), new ArrayList<>(proposal2Figures().values()), showRequest1().getDescription(), showRequest1().getLocation(), showRequest1().getShowDate(), 10, showRequest1().getShowDuration(), "crm_collaborator@shodrone.app", LocalDateTime.now(), proposal1DroneModels());
        proposal.setText(filled);
        return proposal;
    }


    private ShowProposal proposal2() {
        Content content = new Content(
                foundCostumerByNIF(new NIF("286500850")),
                showRequest2().getShowDate(),
                showRequest2().getLocation(),
                showRequest2().getShowDuration(),
                proposal2Figures(), proposal2DroneModels(),
                "crm_collaborator@shodrone.app");

        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);

        List<String> filled = TemplatePlugin.replacePlaceholders(RepositoryProvider.proposalTemplateRepository().findByName("Template EN").get().text(), placeholders);
        content.changeText(filled);
        ShowProposal proposal = new ShowProposal(new Name("Proposal Two"), showRequest2(), RepositoryProvider.proposalTemplateRepository().findByName("Template EN").get(), new ArrayList<>(proposal2Figures().values()), showRequest2().getDescription(), showRequest2().getLocation(), showRequest2().getShowDate(), 15, showRequest2().getShowDuration(), "crm_collaborator@shodrone.app", LocalDateTime.now(), proposal2DroneModels());
        proposal.setText(filled);
        return proposal;
    }

    private ShowProposal proposal3() {
        Content content = new Content(
                foundCostumerByNIF(new NIF("248367080")),
                showRequest3().getShowDate(),
                showRequest3().getLocation(),
                showRequest3().getShowDuration(),
                proposal3Figures(), proposal3DroneModels(),
                "crm_collaborator@shodrone.app");

        Map<String, String> placeholders = TemplatePlugin.buildPlaceholderMap(content);

        List<String> filled = TemplatePlugin.replacePlaceholders(RepositoryProvider.proposalTemplateRepository().findByName("Template PT").get().text(), placeholders);
        content.changeText(filled);

        ShowProposal proposal = new ShowProposal(new Name("Proposal Three"), showRequest3(), RepositoryProvider.proposalTemplateRepository().findByName("Template PT").get(), new ArrayList<>(proposal3Figures().values()), showRequest3().getDescription(), showRequest3().getLocation(), showRequest3().getShowDate(), 20, showRequest3().getShowDuration(), "crm_collaborator@shodrone.app", LocalDateTime.now(), proposal3DroneModels());
        proposal.setText(filled);
        return proposal;
    }


    private Map<DroneModel, Integer> proposal1DroneModels() {
        Map<DroneModel, Integer> models = new HashMap<>();
        models.put(droneModel1, 5);
        models.put(droneModel2, 5);
        return models;
    }

    private Map<DroneModel, Integer> proposal2DroneModels() {
        Map<DroneModel, Integer> models = new HashMap<>();
        models.put(droneModel3, 10);
        models.put(droneModel5, 5);
        return models;
    }

    private Map<DroneModel, Integer> proposal3DroneModels() {
        Map<DroneModel, Integer> models = new HashMap<>();
        models.put(droneModel1, 10);
        models.put(droneModel2, 5);
        models.put(droneModel4, 5);
        return models;
    }

    private Map<Integer, Figure> proposal1Figures() {
        Map<Integer, Figure> f = new HashMap<>();
        f.put(1, foundFigure(1L));
        f.put(2, foundFigure(2L));
        return f;
    }

    private Map<Integer, Figure> proposal2Figures() {
        Map<Integer, Figure> f = new HashMap<>();
        f.put(1, foundFigure(3L));
        f.put(2, foundFigure(4L));
        return f;
    }

    private Map<Integer, Figure> proposal3Figures() {
        Map<Integer, Figure> f = new HashMap<>();
        f.put(1, foundFigure(2L));
        f.put(2, foundFigure(4L));
        return f;
    }

    public void addProposals() {
        RepositoryProvider.showProposalRepository().saveInStore(proposal1());
        RepositoryProvider.showProposalRepository().saveInStore(proposal2());
        RepositoryProvider.showProposalRepository().saveInStore(proposal3());
    }

    // ---- -------------------
    private Costumer foundCostumerByNIF(NIF nif) {
        Optional<Costumer> result = RepositoryProvider.costumerRepository().findByNIF(nif);
        return result.orElse(null);
    }

    private Figure foundFigure(Long id) {
        Optional<Figure> result = RepositoryProvider.figureRepository().findFigure(id);
        return result.orElse(null);
    }
}